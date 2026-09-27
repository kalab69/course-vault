import { useEffect, useState } from 'react';

function ThemeToggle() {
  const [theme, setTheme] = useState(() => localStorage.getItem('theme') || 'auto');

  useEffect(() => {
    if (theme === 'auto') {
      document.documentElement.removeAttribute('data-theme');
    } else {
      document.documentElement.setAttribute('data-theme', theme);
    }
    localStorage.setItem('theme', theme);
  }, [theme]);

  function toggle() {
    setTheme(t => t === 'dark' ? 'light' : 'dark');
  }

  return (
    <button className="theme-toggle" onClick={toggle}>
      {theme === 'dark' ? 'Light mode' : 'Dark mode'}
    </button>
  );
}

export default ThemeToggle;