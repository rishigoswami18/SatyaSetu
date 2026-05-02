"use client";

import React from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';

const navItems = [
  { name: 'Insights', path: '/', icon: '📊' },
  { name: 'Truth Verification', path: '/reports', icon: '🛡️' },
  { name: 'Governance Hub', path: '/schemes', icon: '📋' },
  { name: 'Village Feed', path: '/feed', icon: '🧑‍🌾' },
  { name: 'Community', path: '/users', icon: '👥' },
  { name: 'Settings', path: '/settings', icon: '⚙️' },
];

export default function Sidebar() {
  const pathname = usePathname();

  return (
    <aside className="sidebar">
      <div className="sidebar-brand">
        <span className="brand-logo">SS</span>
        <span className="brand-name">SatyaSetu</span>
      </div>
      
      <nav className="sidebar-nav">
        {navItems.map((item) => (
          <Link 
            key={item.name} 
            href={item.path}
            className={`nav-link ${pathname === item.path ? 'active' : ''}`}
          >
            <span className="nav-icon">{item.icon}</span>
            <span className="nav-text">{item.name}</span>
          </Link>
        ))}
      </nav>

      <div className="sidebar-footer">
        <div className="user-profile">
          <div className="avatar">AD</div>
          <div className="user-info">
            <p className="user-name">Admin User</p>
            <p className="user-role">Super Admin</p>
          </div>
        </div>
      </div>

      <style jsx>{`
        .sidebar {
          width: 280px;
          height: 100vh;
          background: var(--bg-surface);
          border-right: 1px solid var(--glass-border);
          display: flex;
          flex-direction: column;
          position: fixed;
          left: 0;
          top: 0;
          z-index: 100;
        }

        .sidebar-brand {
          padding: 2.5rem 2rem;
          display: flex;
          align-items: center;
          gap: 1rem;
        }

        .brand-logo {
          width: 40px;
          height: 40px;
          background: linear-gradient(135deg, var(--brand-primary), var(--brand-primary-light));
          border-radius: 10px;
          display: flex;
          align-items: center;
          justify-content: center;
          font-weight: 800;
          color: white;
          box-shadow: 0 4px 12px rgba(255, 140, 0, 0.3);
        }

        .brand-name {
          font-size: 1.25rem;
          font-weight: 700;
          letter-spacing: -0.02em;
        }

        .sidebar-nav {
          flex: 1;
          padding: 0 1rem;
          display: flex;
          flex-direction: column;
          gap: 0.5rem;
        }

        .nav-link {
          display: flex;
          align-items: center;
          gap: 1rem;
          padding: 1rem 1.25rem;
          text-decoration: none;
          color: var(--text-muted);
          border-radius: 12px;
          transition: var(--transition-fast);
          font-weight: 500;
        }

        .nav-link:hover {
          background: var(--glass-bg);
          color: var(--text-main);
        }

        .nav-link.active {
          background: rgba(255, 140, 0, 0.1);
          color: var(--brand-primary);
        }

        .nav-icon {
          font-size: 1.25rem;
        }

        .sidebar-footer {
          padding: 2rem 1.5rem;
          border-top: 1px solid var(--glass-border);
        }

        .user-profile {
          display: flex;
          align-items: center;
          gap: 1rem;
          padding: 0.75rem;
          background: var(--bg-surface-elevated);
          border-radius: 16px;
        }

        .avatar {
          width: 40px;
          height: 40px;
          background: var(--bg-surface);
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 0.875rem;
          font-weight: 600;
          border: 1px solid var(--glass-border);
        }

        .user-name {
          font-size: 0.875rem;
          font-weight: 600;
        }

        .user-role {
          font-size: 0.75rem;
          color: var(--text-dim);
        }
      `}</style>
    </aside>
  );
}
