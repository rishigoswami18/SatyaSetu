"use client";

import Sidebar from "@/components/Sidebar";
import { useState } from "react";

export default function SettingsPage() {
  const [activeTab, setActiveTab] = useState("general");

  return (
    <main className="dashboard-container">
      <Sidebar />
      
      <div className="main-content">
        <header className="content-header">
          <div>
            <h1 className="heading-xl">Platform Settings</h1>
            <p className="text-small">Configure core parameters, API integrations, and administrative controls.</p>
          </div>
        </header>

        <section className="settings-layout">
          <div className="glass-card settings-sidebar">
            <button 
              className={`settings-tab ${activeTab === 'general' ? 'active' : ''}`}
              onClick={() => setActiveTab('general')}
            >
              ⚙️ General
            </button>
            <button 
              className={`settings-tab ${activeTab === 'api' ? 'active' : ''}`}
              onClick={() => setActiveTab('api')}
            >
              🔑 API Integrations
            </button>
            <button 
              className={`settings-tab ${activeTab === 'villages' ? 'active' : ''}`}
              onClick={() => setActiveTab('villages')}
            >
              🏘️ Village Registry
            </button>
            <button 
              className={`settings-tab ${activeTab === 'security' ? 'active' : ''}`}
              onClick={() => setActiveTab('security')}
            >
              🛡️ Security
            </button>
          </div>

          <div className="glass-card settings-content">
            {activeTab === 'general' && (
              <div className="tab-pane animate-fade-in">
                <h2 className="heading-md">General Configuration</h2>
                <div className="form-group">
                  <label>Platform Name</label>
                  <input type="text" defaultValue="SatyaSetu Community" />
                </div>
                <div className="form-group">
                  <label>Support Email</label>
                  <input type="email" defaultValue="support@satyasetu.org" />
                </div>
                <div className="form-group">
                  <label>Default Language</label>
                  <select>
                    <option>Hindi (Default)</option>
                    <option>English</option>
                  </select>
                </div>
                <button className="btn-primary">Save Changes</button>
              </div>
            )}

            {activeTab === 'api' && (
              <div className="tab-pane animate-fade-in">
                <h2 className="heading-md">API Credentials</h2>
                <p className="text-small">Manage keys for AI and Mapping services.</p>
                <div className="api-card">
                  <div className="api-header">
                    <span>Groq Cloud AI (Llama 3)</span>
                    <span className="status-online">Online</span>
                  </div>
                  <input type="password" value="••••••••••••••••••••••••" readOnly />
                </div>
                <div className="api-card">
                  <div className="api-header">
                    <span>Mappls SDK Key</span>
                    <span className="status-online">Online</span>
                  </div>
                  <input type="password" value="••••••••••••••••••••••••" readOnly />
                </div>
                <button className="btn-secondary">Update API Keys</button>
              </div>
            )}

            {activeTab === 'villages' && (
              <div className="tab-pane animate-fade-in">
                <div className="section-header-row">
                  <h2 className="heading-md">Active Village Registry</h2>
                  <button className="btn-icon-plus">+ Add Village</button>
                </div>
                <div className="village-list">
                  {['Sitapur', 'Rampur', 'Basti', 'Kampur'].map(v => (
                    <div key={v} className="village-item">
                      <span>{v}</span>
                      <button className="btn-text-danger">Deactivate</button>
                    </div>
                  ))}
                </div>
              </div>
            )}
          </div>
        </section>
      </div>

      <style jsx>{`
        .dashboard-container { display: flex; min-height: 100vh; background: var(--bg-deep); }
        .main-content { margin-left: 280px; flex: 1; padding: 3rem; }
        .content-header { margin-bottom: 3rem; }

        .settings-layout { display: grid; grid-template-columns: 280px 1fr; gap: 2rem; }

        .settings-sidebar { padding: 1.5rem; display: flex; flex-direction: column; gap: 0.5rem; height: fit-content; }
        .settings-tab {
          text-align: left; background: none; border: none; padding: 1rem; border-radius: 12px;
          color: var(--text-muted); font-weight: 600; cursor: pointer; transition: var(--transition-fast);
        }
        .settings-tab:hover { background: var(--glass-bg); color: var(--text-main); }
        .settings-tab.active { background: rgba(255, 140, 0, 0.1); color: var(--brand-primary); }

        .settings-content { padding: 2.5rem; }
        .tab-pane { display: flex; flex-direction: column; gap: 2rem; }

        .form-group { display: flex; flex-direction: column; gap: 0.75rem; max-width: 450px; }
        .form-group label { font-size: 0.75rem; text-transform: uppercase; color: var(--text-dim); font-weight: 700; letter-spacing: 0.05em; }
        .form-group input, .form-group select {
          background: var(--bg-deep); border: 1px solid var(--glass-border); padding: 0.875rem;
          border-radius: 10px; color: white; outline: none; font-size: 0.95rem;
        }

        .api-card { background: var(--bg-deep); border: 1px solid var(--glass-border); padding: 1.5rem; border-radius: 12px; display: flex; flex-direction: column; gap: 1rem; }
        .api-header { display: flex; justify-content: space-between; font-size: 0.85rem; font-weight: 600; }
        .status-online { color: var(--status-success); font-size: 0.7rem; text-transform: uppercase; }
        .api-card input { background: rgba(255, 255, 255, 0.05); border: none; padding: 0.75rem; border-radius: 8px; color: var(--text-dim); font-family: monospace; }

        .section-header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; }
        .btn-icon-plus { background: var(--brand-primary); border: none; color: white; padding: 0.5rem 1rem; border-radius: 8px; font-weight: 700; cursor: pointer; }

        .village-list { display: flex; flex-direction: column; gap: 1rem; }
        .village-item {
          display: flex; justify-content: space-between; align-items: center;
          padding: 1rem; background: var(--bg-deep); border-radius: 10px; border: 1px solid var(--glass-border);
        }
        .btn-text-danger { background: none; border: none; color: var(--status-danger); font-size: 0.8rem; font-weight: 600; cursor: pointer; }

        .btn-primary {
          background: linear-gradient(135deg, var(--brand-primary), var(--brand-primary-light));
          border: none; padding: 1rem 2rem; border-radius: 12px; color: white; font-weight: 700; cursor: pointer; width: fit-content;
        }
        .btn-secondary { background: var(--bg-surface-elevated); border: 1px solid var(--glass-border); color: white; padding: 1rem 2rem; border-radius: 12px; font-weight: 600; cursor: pointer; width: fit-content; }
      `}</style>
    </main>
  );
}
