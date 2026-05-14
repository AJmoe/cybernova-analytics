<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String error = (String) request.getAttribute("error");
    String success = request.getParameter("success");
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login - CyberNova Analytics Ltd</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css?v=20260508c">
    <style>
        .login-container {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: radial-gradient(circle at top, var(--bg-soft) 0%, var(--bg) 50%, #08152f 100%);
            padding: 1rem;
        }

        .login-box {
            width: 100%;
            max-width: 420px;
        }

        .login-panel {
            background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
            border: none;
            box-shadow: 0 24px 64px rgba(0, 0, 0, 0.25);
            position: relative;
            overflow: hidden;
        }

        .login-panel::before {
            content: '';
            position: absolute;
            top: -100px;
            right: -100px;
            width: 300px;
            height: 300px;
            background: radial-gradient(circle, rgba(242, 169, 0, 0.1) 0%, transparent 70%);
            border-radius: 50%;
            pointer-events: none;
        }

        .login-panel::after {
            content: '';
            position: absolute;
            bottom: -80px;
            left: -50px;
            width: 250px;
            height: 250px;
            background: radial-gradient(circle, rgba(43, 95, 191, 0.08) 0%, transparent 70%);
            border-radius: 50%;
            pointer-events: none;
        }

        .login-panel > * {
            position: relative;
            z-index: 1;
        }

        .login-panel h1 {
            color: #0a1f44;
            font-size: 1.8rem;
            font-weight: 800;
            background: linear-gradient(135deg, #0a1f44 0%, #2b5fbf 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin-bottom: 0.5rem;
        }

        .login-panel > p {
            color: #7a8fa3;
            font-size: 0.95rem;
            margin-bottom: 1.5rem;
        }

        .form-label {
            color: #0a1f44;
            font-weight: 600;
            font-size: 0.95rem;
            margin-bottom: 0.5rem;
        }

        .form-control {
            border: 1.5px solid rgba(15, 44, 102, 0.15);
            border-radius: 0.8rem;
            padding: 0.85rem 1rem;
            color: #0a1f44;
            font-size: 0.95rem;
            transition: all 0.3s ease;
            background: #fff;
        }

        .form-control:focus {
            outline: none;
            border-color: var(--accent);
            box-shadow: 0 0 0 3px rgba(242, 169, 0, 0.15);
            color: #0a1f44;
            background: #fff;
        }

        .form-control::placeholder {
            color: #a0afc4;
        }

        .login-panel .alert {
            border-radius: 0.8rem;
            border: none;
            margin-bottom: 1.25rem;
            padding: 0.95rem 1rem;
        }

        .alert-success {
            background: rgba(34, 197, 94, 0.1);
            color: #166534;
            border-left: 4px solid #22c55e;
        }

        .alert-danger {
            background: rgba(239, 68, 68, 0.1);
            color: #7f1d1d;
            border-left: 4px solid #ef4444;
        }

        .btn-login {
            width: 100%;
            padding: 0.9rem 1.5rem;
            font-size: 1rem;
            font-weight: 700;
            border-radius: 0.8rem;
            margin-top: 1rem;
            transition: all 0.3s ease;
            border: none;
            background: linear-gradient(135deg, var(--accent) 0%, var(--accent-dark) 100%);
            color: var(--bg);
        }

        .btn-login:hover {
            background: linear-gradient(135deg, var(--accent-dark) 0%, #c77e00 100%);
            box-shadow: 0 12px 32px rgba(242, 169, 0, 0.3);
            transform: translateY(-2px);
        }

        .btn-login:active {
            transform: translateY(0);
        }

        .login-footer {
            text-align: center;
            margin-top: 1.5rem;
            padding-top: 1.5rem;
            border-top: 1px solid rgba(15, 44, 102, 0.1);
            color: #7a8fa3;
            font-size: 0.9rem;
        }

        .login-footer a {
            color: var(--blue);
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .login-footer a:hover {
            color: var(--accent);
        }

        .login-brand {
            text-align: center;
            margin-bottom: 2rem;
        }

        .login-brand h2 {
            color: var(--text);
            font-size: 1.4rem;
            font-weight: 800;
            background: linear-gradient(135deg, var(--text) 0%, rgba(242, 169, 0, 0.8) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin-bottom: 0.25rem;
        }

        .login-brand p {
            color: var(--muted);
            font-size: 0.85rem;
            letter-spacing: 0.5px;
        }
    </style>
</head>
<body class="admin-login-page">
<div class="login-container">
    <div class="login-box">
        <div class="login-brand">
            <h2>CyberNova Analytics</h2>
            <p>Admin Portal</p>
        </div>

        <div class="panel login-panel">
            <h1>Admin Login</h1>
            <p>Secure access to dashboard and analytics.</p>

            <% if (success != null) { %>
                <div class="alert alert-success">
                    <% if ("adminRegistered".equals(success)) { %>
                        New admin account created successfully. Please sign in.
                    <% } else { %>
                        Action completed successfully.
                    <% } %>
                </div>
            <% } %>

            <% if (error != null) { %>
                <div class="alert alert-danger"><%= error %></div>
            <% } %>

            <form method="post" novalidate>
                <div class="mb-3">
                    <label class="form-label" for="username">Username</label>
                    <input class="form-control" type="text" id="username" name="username"
                           placeholder="Enter your username" required minlength="3" maxlength="20">
                </div>

                <div class="mb-3">
                    <label class="form-label" for="password">Password</label>
                    <input class="form-control" type="password" id="password" name="password"
                           placeholder="Enter your password" required minlength="8">
                </div>

                <button class="btn btn-login" type="submit">Sign in</button>
            </form>

            <div class="login-footer">
                Contact admin for access • <a href="${pageContext.request.contextPath}/home">Back to home</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
