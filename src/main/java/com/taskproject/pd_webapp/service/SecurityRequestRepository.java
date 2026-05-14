package com.taskproject.pd_webapp.service;

import com.taskproject.pd_webapp.model.SecurityRequest;
import com.taskproject.pd_webapp.model.RegionalDemand;
import com.taskproject.pd_webapp.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SecurityRequestRepository {
    private static final SecurityRequestRepository INSTANCE = new SecurityRequestRepository();

    private SecurityRequestRepository() {
        DBConnection.ensureInitialized();
        seedIfEmpty();
    }

    public static SecurityRequestRepository getInstance() {
        return INSTANCE;
    }

    public SecurityRequest save(SecurityRequest request) {
        request.setStatus(normalizeStatus(request.getStatus()));
        request.setPriority(normalizePriority(request.getPriority()));
        request.setCreatedAt(request.getCreatedAt() == null ? LocalDateTime.now() : request.getCreatedAt());

        String insertClientSql = "INSERT INTO clients (name, email, phone, organization, country, job_title) VALUES (?, ?, ?, ?, ?, ?)";
        String insertRequestSql = "INSERT INTO security_requests (client_id, type_of_security_issue, description, status, priority, region, assigned_to, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement clientStatement = connection.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS)) {
                bindClient(clientStatement, request);
                clientStatement.executeUpdate();
                try (ResultSet keys = clientStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        request.setClientId(keys.getInt(1));
                    }
                }
            }

            try (PreparedStatement requestStatement = connection.prepareStatement(insertRequestSql, Statement.RETURN_GENERATED_KEYS)) {
                bindSecurityRequest(requestStatement, request);
                requestStatement.executeUpdate();
                try (ResultSet keys = requestStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        request.setRequestId(keys.getInt(1));
                    }
                }
            }

            insertRequestAnalytics(connection, request);
            upsertRegionalDemand(connection, request);

            connection.commit();
            return request;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }
            throw new IllegalStateException("Unable to save security request", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException ignored) {
                    // Ignore cleanup errors.
                }
            }
        }
    }

    public SecurityRequest findById(long id) {
        String sql = "SELECT sr.request_id, sr.client_id, sr.type_of_security_issue, sr.description, sr.status, sr.priority, sr.region, sr.assigned_to, sr.created_at, sr.updated_at, sr.resolved_at, " +
                "c.name AS client_name, c.email AS client_email, c.phone AS client_phone, c.organization, c.country, c.job_title " +
                "FROM security_requests sr JOIN clients c ON sr.client_id = c.client_id WHERE sr.request_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRequest(rs);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load security request", e);
        }

        return null;
    }

    public boolean updateStatus(long id, String status) {
        String sql = "UPDATE security_requests SET status = ?, updated_at = CURRENT_TIMESTAMP WHERE request_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, normalizeStatus(status));
            statement.setLong(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to update security request status", e);
        }
    }

    public List<SecurityRequest> findAll() {
        String sql = "SELECT sr.request_id, sr.client_id, sr.type_of_security_issue, sr.description, sr.status, sr.priority, sr.region, sr.assigned_to, sr.created_at, sr.updated_at, sr.resolved_at, " +
                "c.name AS client_name, c.email AS client_email, c.phone AS client_phone, c.organization, c.country, c.job_title " +
                "FROM security_requests sr JOIN clients c ON sr.client_id = c.client_id ORDER BY sr.created_at DESC";
        List<SecurityRequest> requests = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                requests.add(mapRequest(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load security requests", e);
        }

        return requests;
    }

    public List<SecurityRequest> findFiltered(String issueType, String country, String search) {
        StringBuilder sql = new StringBuilder("SELECT sr.request_id, sr.client_id, sr.type_of_security_issue, sr.description, sr.status, sr.priority, sr.region, sr.assigned_to, sr.created_at, sr.updated_at, sr.resolved_at, ");
        sql.append("c.name AS client_name, c.email AS client_email, c.phone AS client_phone, c.organization, c.country, c.job_title ");
        sql.append("FROM security_requests sr JOIN clients c ON sr.client_id = c.client_id WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (!normalize(issueType).isBlank()) {
            sql.append(" AND LOWER(sr.type_of_security_issue) = ?");
            params.add(normalize(issueType));
        }
        if (!normalize(country).isBlank()) {
            sql.append(" AND LOWER(c.country) = ?");
            params.add(normalize(country));
        }
        if (!normalize(search).isBlank()) {
            sql.append(" AND (LOWER(c.name) LIKE ? OR LOWER(c.organization) LIKE ? OR LOWER(c.job_title) LIKE ? OR LOWER(sr.description) LIKE ?)");
            String like = "%" + normalize(search) + "%";
            params.add(like);
            params.add(like);
            params.add(like);
            params.add(like);
        }

        sql.append(" ORDER BY sr.created_at DESC");
        List<SecurityRequest> requests = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                statement.setString(i + 1, params.get(i));
            }
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    requests.add(mapRequest(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load filtered requests", e);
        }

        return requests;
    }

    public List<String> getDistinctIssueTypes() {
        return loadDistinctValues("security_requests", "type_of_security_issue");
    }

    public List<String> getDistinctCountries() {
        return loadDistinctValues("clients", "country");
    }

    public List<RegionalDemand> getRegionalDemand(int limit) {
        String sql = "SELECT demand_id, region, country, service_type, request_count, demand_level, last_updated " +
                "FROM regional_demand ORDER BY request_count DESC, last_updated DESC LIMIT ?";
        List<RegionalDemand> demandList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Math.max(limit, 1));
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    demandList.add(mapRegionalDemand(rs));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load regional demand", e);
        }

        return demandList;
    }

    private List<String> loadDistinctValues(String table, String column) {
        String sql = "SELECT DISTINCT " + column + " FROM " + table + " ORDER BY " + column;
        List<String> values = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                String value = rs.getString(1);
                if (value != null && !value.isBlank()) {
                    values.add(value);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to load distinct values", e);
        }

        return values;
    }

    private void seedIfEmpty() {
        if (!findAll().isEmpty()) {
            return;
        }

        saveSeed("Amina", "amina@northwave.bw", "+267 71234567", "NorthWave Bank", "Botswana", "Risk Officer", "Phishing Response", "Urgent phishing campaign targeting staff mailbox access.");
        saveSeed("Tebogo", "tebogo@metrocare.co.bw", "+267 72123456", "MetroCare Clinics", "Botswana", "IT Manager", "Vulnerability Assessment", "Need a technical scan for exposed services and patch priorities.");
        saveSeed("Sipho", "sipho@logisafrica.za", "+27 821234567", "Logis Africa", "South Africa", "Security Lead", "Incident Response", "Suspicious lateral movement detected on warehouse endpoints.");
        saveSeed("Naledi", "naledi@financehub.na", "+264 811234567", "Finance Hub Namibia", "Namibia", "Compliance Manager", "Security Awareness Training", "Requesting staff training plus policy review after recent audit findings.");
        saveSeed("Kabelo", "kabelo@publicworks.gov.bw", "+267 75432100", "Ministry Project Office", "Botswana", "Director", "Cloud Hardening", "Need review of cloud account controls and privileged access configuration.");
    }

    private void saveSeed(String name, String email, String phone, String organization, String country, String jobTitle, String issueType, String description) {
        SecurityRequest request = new SecurityRequest();
        request.setClientName(name);
        request.setClientEmail(email);
        request.setClientPhone(phone);
        request.setOrganization(organization);
        request.setCountry(country);
        request.setJobTitle(jobTitle);
        request.setTypeOfSecurityIssue(issueType);
        request.setDescription(description);
        save(request);
    }

    private void bindClient(PreparedStatement statement, SecurityRequest request) throws SQLException {
        statement.setString(1, request.getClientName());
        statement.setString(2, request.getClientEmail());
        statement.setString(3, request.getClientPhone());
        statement.setString(4, request.getOrganization());
        statement.setString(5, request.getCountry());
        statement.setString(6, request.getJobTitle());
    }

    private void bindSecurityRequest(PreparedStatement statement, SecurityRequest request) throws SQLException {
        statement.setInt(1, request.getClientId());
        statement.setString(2, request.getTypeOfSecurityIssue());
        statement.setString(3, request.getDescription());
        statement.setString(4, request.getStatus());
        statement.setString(5, request.getPriority());
        if (request.getRegion() == null || request.getRegion().isBlank()) {
            statement.setNull(6, java.sql.Types.VARCHAR);
        } else {
            statement.setString(6, request.getRegion());
        }
        if (request.getAssignedTo() == null) {
            statement.setNull(7, java.sql.Types.INTEGER);
        } else {
            statement.setInt(7, request.getAssignedTo());
        }
        statement.setTimestamp(8, Timestamp.valueOf(request.getCreatedAt()));
    }

    private SecurityRequest mapRequest(ResultSet rs) throws SQLException {
        SecurityRequest request = new SecurityRequest();
        request.setRequestId(rs.getInt("request_id"));
        request.setClientId(rs.getInt("client_id"));
        request.setTypeOfSecurityIssue(rs.getString("type_of_security_issue"));
        request.setDescription(rs.getString("description"));
        request.setStatus(rs.getString("status"));
        request.setPriority(rs.getString("priority"));
        request.setRegion(rs.getString("region"));
        int assignedTo = rs.getInt("assigned_to");
        request.setAssignedTo(rs.wasNull() ? null : assignedTo);

        Timestamp created = rs.getTimestamp("created_at");
        request.setCreatedAt(created != null ? created.toLocalDateTime() : null);
        Timestamp updated = rs.getTimestamp("updated_at");
        request.setUpdatedAt(updated != null ? updated.toLocalDateTime() : null);
        Timestamp resolved = rs.getTimestamp("resolved_at");
        request.setResolvedAt(resolved != null ? resolved.toLocalDateTime() : null);

        request.setClientName(rs.getString("client_name"));
        request.setClientEmail(rs.getString("client_email"));
        request.setClientPhone(rs.getString("client_phone"));
        request.setOrganization(rs.getString("organization"));
        request.setCountry(rs.getString("country"));
        request.setJobTitle(rs.getString("job_title"));
        return request;
    }

    private RegionalDemand mapRegionalDemand(ResultSet rs) throws SQLException {
        RegionalDemand demand = new RegionalDemand();
        demand.setDemandId(rs.getInt("demand_id"));
        demand.setRegion(rs.getString("region"));
        demand.setCountry(rs.getString("country"));
        demand.setServiceType(rs.getString("service_type"));
        demand.setRequestCount(rs.getInt("request_count"));
        demand.setDemandLevel(rs.getString("demand_level"));
        Timestamp updated = rs.getTimestamp("last_updated");
        demand.setLastUpdated(updated != null ? updated.toLocalDateTime() : null);
        return demand;
    }

    private void insertRequestAnalytics(Connection connection, SecurityRequest request) throws SQLException {
        String sql = "INSERT INTO request_analytics (request_id, service_type, region, country, request_count, recorded_date) VALUES (?, ?, ?, ?, 1, CURRENT_TIMESTAMP)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, request.getRequestId());
            statement.setString(2, request.getTypeOfSecurityIssue());
            String region = normalizeRegion(request);
            statement.setString(3, region);
            statement.setString(4, request.getCountry());
            statement.executeUpdate();
        }
    }

    private void upsertRegionalDemand(Connection connection, SecurityRequest request) throws SQLException {
        String region = normalizeRegion(request);
        String country = request.getCountry();
        String serviceType = request.getTypeOfSecurityIssue();

        String selectSql = "SELECT demand_id, request_count FROM regional_demand WHERE region = ? AND country = ? AND service_type = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
            selectStatement.setString(1, region);
            selectStatement.setString(2, country);
            selectStatement.setString(3, serviceType);
            try (ResultSet rs = selectStatement.executeQuery()) {
                if (rs.next()) {
                    int demandId = rs.getInt("demand_id");
                    int count = rs.getInt("request_count") + 1;
                    String demandLevel = computeDemandLevel(count);
                    String updateSql = "UPDATE regional_demand SET request_count = ?, demand_level = ?, last_updated = CURRENT_TIMESTAMP WHERE demand_id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                        updateStatement.setInt(1, count);
                        updateStatement.setString(2, demandLevel);
                        updateStatement.setInt(3, demandId);
                        updateStatement.executeUpdate();
                    }
                } else {
                    String demandLevel = computeDemandLevel(1);
                    String insertSql = "INSERT INTO regional_demand (region, country, service_type, request_count, demand_level) VALUES (?, ?, ?, 1, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                        insertStatement.setString(1, region);
                        insertStatement.setString(2, country);
                        insertStatement.setString(3, serviceType);
                        insertStatement.setString(4, demandLevel);
                        insertStatement.executeUpdate();
                    }
                }
            }
        }
    }

    private String computeDemandLevel(int requestCount) {
        if (requestCount >= 10) {
            return "CRITICAL";
        }
        if (requestCount >= 6) {
            return "HIGH";
        }
        if (requestCount >= 3) {
            return "MEDIUM";
        }
        return "LOW";
    }

    private String normalizeRegion(SecurityRequest request) {
        String region = request.getRegion();
        if (region == null || region.isBlank()) {
            region = request.getCountry();
        }
        return region == null ? "Unknown" : region.trim();
    }

    private String normalizeStatus(String value) {
        return value == null || value.isBlank() ? "NEW" : value.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizePriority(String value) {
        return value == null || value.isBlank() ? "MEDIUM" : value.trim().toUpperCase(Locale.ROOT);
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
