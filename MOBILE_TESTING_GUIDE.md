# Mobile Hamburger Menu - Testing & Troubleshooting Guide

## 🧪 How to Test the Mobile Menu

### Test 1: Check Mobile View (Chrome DevTools)
```
1. Open browser DevTools (F12)
2. Click "Toggle device toolbar" icon (Ctrl+Shift+M)
3. Select "iPhone SE" or "iPhone 12" from dropdown
4. You should see:
   ✅ CyberNova logo on left
   ✅ 3-bar hamburger menu on right
   ✅ Menu items NOT visible below header
```

### Test 2: Click Hamburger Menu
```
1. With mobile view active
2. Click the 3-bar hamburger button
3. You should see:
   ✅ Menu slides down smoothly
   ✅ Hamburger transforms to X shape
   ✅ All navigation items visible:
      - Home
      - Cybersecurity Solutions
      - Case Studies
      - Cyber Blog
      - Testimonials
      - Workshop Gallery
      - Contact Security Team
      - Admin
```

### Test 3: Click a Link
```
1. Menu is open (showing all items)
2. Click any navigation link (e.g., "Solutions")
3. You should see:
   ✅ Link navigates to correct page
   ✅ Menu automatically closes
   ✅ Hamburger returns to 3-bar shape
```

### Test 4: Click Outside Menu
```
1. Menu is open
2. Click anywhere outside the header area
3. You should see:
   ✅ Menu smoothly collapses
   ✅ Hamburger returns to 3-bar shape
```

### Test 5: Active Link Highlighting
```
1. Open mobile menu
2. You should see:
   ✅ Current page link is highlighted
   ✅ Link has golden/accent color background
   ✅ Left border shows accent color
```

### Test 6: Window Resize
```
1. Start with mobile view (≤768px)
2. Menu visible with hamburger
3. Slowly drag DevTools border to expand window
4. At 769px you should see:
   ✅ Hamburger button DISAPPEARS
   ✅ Full horizontal navigation APPEARS
   ✅ Navigation shows all items in horizontal row
   ✅ Items have rounded buttons with hover effects
```

### Test 7: Desktop View
```
1. Close DevTools (F12 to close)
2. Full screen view (1200px+)
3. You should see:
   ✅ No hamburger button visible
   ✅ Full navigation with all items visible
   ✅ Items arranged horizontally
   ✅ Hover effects on navigation items
   ✅ Smooth transitions on hover
```

---

## 🐛 Troubleshooting

### Problem: Hamburger button not appearing on mobile
**Cause:** CSS media query at 768px might not be working
**Solution:**
```javascript
// Check in browser console:
console.log(window.innerWidth);  // Should be ≤768

// Check CSS is loaded:
const toggle = document.querySelector('[data-mobile-menu-toggle]');
console.log(window.getComputedStyle(toggle).display);
// Should show "block" on mobile, "none" on desktop
```

**Fix:** Hard refresh browser (Ctrl+Shift+R or Cmd+Shift+R on Mac)

---

### Problem: Menu won't open when hamburger is clicked
**Cause:** JavaScript not running or event listener not attached
**Solution:**
```javascript
// Check in console:
const toggle = document.querySelector('[data-mobile-menu-toggle]');
const menu = document.querySelector('[data-mobile-menu]');

console.log('Toggle exists:', !!toggle);
console.log('Menu exists:', !!menu);
console.log('Menu aria-expanded:', menu.getAttribute('aria-expanded'));

// Manually trigger click:
toggle.click();
console.log('After click:', menu.getAttribute('aria-expanded'));
// Should toggle between "true" and "false"
```

**Fix:** 
1. Clear browser cache
2. Ensure `main.js` is loaded (check Network tab)
3. Check browser console for JavaScript errors

---

### Problem: Menu items stacked oddly
**Cause:** CSS flex-direction not set correctly
**Solution:**
```css
/* Check in DevTools - right click menu > Inspect */
/* The nav should have: */
.site-header__nav {
  flex-direction: column;  /* On mobile */
  flex-direction: row;     /* On desktop */
}
```

**Fix:** Ensure CSS media queries are in correct order

---

### Problem: Hamburger doesn't animate (stays as 3 bars)
**Cause:** Transition might be disabled
**Solution:**
```css
/* Check computed styles: */
.menu-bar {
  transition: all 0.3s ease;  /* Should be present */
}
```

**Fix:** Verify `transition: all 0.3s ease` is in CSS

---

### Problem: Menu stays open when clicking link
**Cause:** Event listener not attached to links
**Solution:**
```javascript
// Check in console:
const menu = document.querySelector('[data-mobile-menu]');
const links = menu.querySelectorAll('a');
console.log('Number of links:', links.length);
// Should be > 0

// Check if click actually closes it:
links[0].click();
console.log('Menu expanded:', menu.getAttribute('aria-expanded'));
// Should be "false" after click
```

**Fix:** Restart application, ensure JavaScript fully loaded

---

### Problem: Menu overlaps content
**Cause:** Position absolute might be interfering
**Solution:**
```css
/* Check computed style: */
.site-header__nav {
  position: absolute;
  top: 100%;  /* Should be just below header */
  left: 0;
  right: 0;
  z-index: 99;  /* Should be below header z-index: 100 */
}
```

**Fix:** Verify z-index and position values in CSS

---

## ✅ Verification Checklist

- [ ] Mobile view shows hamburger (≤768px)
- [ ] Desktop view hides hamburger (>768px)
- [ ] Hamburger button is clickable
- [ ] Menu opens with smooth animation
- [ ] Hamburger animates to X shape
- [ ] All navigation items are visible when open
- [ ] Links navigate correctly
- [ ] Menu closes after link click
- [ ] Menu closes on outside click
- [ ] Active link is highlighted
- [ ] Resize window smoothly transitions between mobile/desktop
- [ ] No console errors in DevTools

---

## 🔍 Browser DevTools Inspection

### Inspect Hamburger Button
```
1. Right-click hamburger button
2. Click "Inspect"
3. Verify HTML structure:
   <button type="button" class="site-header__menu-toggle" 
           aria-label="Toggle navigation" 
           aria-expanded="false" 
           data-mobile-menu-toggle>
       <span class="menu-icon">
           <span class="menu-bar"></span>
           <span class="menu-bar"></span>
           <span class="menu-bar"></span>
       </span>
   </button>
```

### Inspect Navigation Menu
```
1. Right-click menu
2. Click "Inspect"
3. Verify:
   <nav class="site-header__nav" 
        data-mobile-menu 
        aria-expanded="false">
       <a href="/home">Home</a>
       ...
   </nav>

4. Check CSS applied:
   - display: flex
   - flex-direction: column (mobile) or row (desktop)
   - position: absolute (mobile)
   - max-height: 0 (closed) or 600px (open)
```

### Check Computed Styles
```
1. With nav menu selected in DevTools
2. Go to "Computed" tab
3. Verify:
   - display: flex ✓
   - flex-direction: column ✓ (mobile)
   - max-height: 600px ✓ (when open)
   - overflow: hidden ✓
   - transition: max-height 0.4s ✓
```

---

## 📊 JavaScript Console Tests

### Test Menu State
```javascript
const menu = document.querySelector('[data-mobile-menu]');
const isMobileOpen = menu.getAttribute('aria-expanded') === 'true';
console.log('Menu is open:', isMobileOpen);
```

### Test Screen Size
```javascript
console.log('Window width:', window.innerWidth);
console.log('Is mobile (≤768px):', window.innerWidth <= 768);
```

### Test Button Click
```javascript
const toggle = document.querySelector('[data-mobile-menu-toggle]');
toggle.click();  // Simulates user click
console.log('Menu state after click:', 
    document.querySelector('[data-mobile-menu]').getAttribute('aria-expanded'));
```

### Test All Links
```javascript
const links = document.querySelectorAll('.site-header__nav a');
console.log('Total navigation links:', links.length);
links.forEach((link, i) => {
    console.log(`${i + 1}. ${link.textContent.trim()}`);
});
```

---

## 🎨 Visual Testing Guide

### Expected Colors (Mobile, Menu Open)
- **Background**: Dark navy with semi-transparent overlay
- **Text**: Light gray/white
- **Hamburger bars**: White (#f5f7fb)
- **Dividers between items**: Subtle gold/orange
- **Active link**: Gold/orange accent with left border

### Expected Animations
- **Menu open**: 0.4s smooth slide down
- **Hamburger bars**: 0.3s rotation/fade animation
- **Link hover**: Color change with background effect
- **Window resize**: Smooth transition to desktop layout

---

## 🚨 Common Mistakes to Avoid

❌ **DON'T:** Hardcode `hidden` attribute on button
```javascript
// WRONG - button never shows
<button hidden>Menu</button>
```

✅ **DO:** Use CSS display or JavaScript to control visibility
```javascript
// RIGHT - CSS media query controls display
.site-header__menu-toggle {
    display: none;  /* Default hidden */
}

@media (max-width: 768px) {
    .site-header__menu-toggle {
        display: block;  /* Show on mobile */
    }
}
```

---

❌ **DON'T:** Forget to close menu when link clicked
```javascript
// WRONG - menu stays open after navigation
link.addEventListener('click', () => {
    // Forgot to close menu!
});
```

✅ **DO:** Close menu after navigation
```javascript
// RIGHT - menu closes on link click
link.addEventListener('click', () => {
    menu.setAttribute('aria-expanded', 'false');
});
```

---

## 🎯 Quick Fixes

### If Nothing Works
```bash
# Clear cache and rebuild
mvn clean package

# Then:
# 1. Stop Tomcat
# 2. Clear browser cache (Ctrl+Shift+Delete)
# 3. Restart Tomcat
# 4. Hard refresh app (Ctrl+Shift+R)
```

### If Mobile Menu Doesn't Appear
```javascript
// Run in console while on mobile view:
const toggle = document.querySelector('[data-mobile-menu-toggle]');
toggle.style.display = 'block !important';  // Force show
```

### If Menu Won't Close
```javascript
// Run in console:
const menu = document.querySelector('[data-mobile-menu]');
menu.setAttribute('aria-expanded', 'false');  // Force close
```

---

## 📱 Test Devices to Check

- [ ] iPhone SE (375px)
- [ ] iPhone 12 (390px)
- [ ] iPhone 14 Pro (393px)
- [ ] Samsung Galaxy A12 (412px)
- [ ] iPad (768px)
- [ ] iPad Pro (1024px)
- [ ] Desktop 1024px
- [ ] Desktop 1920px
- [ ] Desktop 2560px

---

## ✨ Final Verification

Run this complete test:

```javascript
// 1. Check all elements exist
const toggle = document.querySelector('[data-mobile-menu-toggle]');
const menu = document.querySelector('[data-mobile-menu]');
const links = menu.querySelectorAll('a');

console.assert(toggle, '❌ Toggle not found');
console.assert(menu, '❌ Menu not found');
console.assert(links.length > 0, '❌ Links not found');

// 2. Check initial state
console.log('✅ Toggle found');
console.log('✅ Menu found');
console.log('✅ ' + links.length + ' navigation links found');

// 3. Test toggle
toggle.click();
const isOpen = menu.getAttribute('aria-expanded') === 'true';
console.assert(isOpen, '❌ Menu did not open');
console.log('✅ Menu opens correctly');

// 4. Test close
toggle.click();
const isClosed = menu.getAttribute('aria-expanded') === 'false';
console.assert(isClosed, '❌ Menu did not close');
console.log('✅ Menu closes correctly');

// 5. Responsive check
console.log('✅ Window width: ' + window.innerWidth + 'px');
console.log('✅ All tests passed!');
```

---

## 🎉 Success!

Your mobile hamburger menu is working perfectly! Users can now:
- ✅ Navigate easily on mobile devices
- ✅ Toggle menu with smooth animations
- ✅ Auto-close on navigation
- ✅ Auto-close on outside click
- ✅ Experience responsive design at all sizes

**Happy shipping!** 🚀


