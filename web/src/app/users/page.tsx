"use client";

import Sidebar from "@/components/Sidebar";
import { useState, useEffect } from "react";
import { FirebaseService } from "@/services/firebaseService";

export default function UsersPage() {
  const [users, setUsers] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const unsubscribe = FirebaseService.observeUsers((data) => {
      setUsers(data);
      setLoading(false);
    });
    return () => unsubscribe();
  }, []);

  const updateUserStatus = async (userId: string, status: string) => {
    try {
      await FirebaseService.updateUserStatus(userId, status);
    } catch (error) {
      console.error("Update failed", error);
    }
  };

  const filteredUsers = users.filter(u => 
    u.name.toLowerCase().includes(searchTerm.toLowerCase()) || 
    u.village.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <main className="dashboard-container">
      <Sidebar />
      
      <div className="main-content">
        <header className="content-header">
          <div>
            <h1 className="heading-xl">Community Members</h1>
            <p className="text-small">Manage village members, monitor trust scores, and ensure community health.</p>
          </div>
          <div className="search-box glass-card">
            <span className="search-icon">🔍</span>
            <input 
              type="text" 
              placeholder="Search by name or village..." 
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
        </header>

        <section className="users-overview">
          <div className="glass-card table-section">
            <div className="table-wrapper">
              <table className="custom-table">
                <thead>
                  <tr>
                    <th>Member Details</th>
                    <th>Village</th>
                    <th>Trust Rating</th>
                    <th>Contributions</th>
                    <th>Status</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredUsers.map((user) => (
                    <tr key={user.id}>
                      <td>
                        <div className="user-info-cell">
                          <div className="avatar-med">{(user.name || 'U')[0]}</div>
                          <div>
                            <p className="user-name">{user.name || 'Anonymous'}</p>
                            <p className="user-id">{user.id}</p>
                          </div>
                        </div>
                      </td>
                      <td>{user.village || user.villageId || 'N/A'}</td>
                      <td>
                        <div className="trust-meter">
                          <div className="meter-bg">
                            <div 
                              className={`meter-fill ${(user.trustScore || 90) > 80 ? 'high' : (user.trustScore || 90) > 40 ? 'mid' : 'low'}`}
                              style={{ width: `${user.trustScore || 90}%` }}
                            ></div>
                          </div>
                          <span className="trust-value">{user.trustScore || 90}%</span>
                        </div>
                      </td>
                      <td>
                        <span className="contribution-pill">{user.contributions || 0} Posts</span>
                      </td>
                      <td>
                        <span className={`status-pill ${(user.status || 'Active').toLowerCase()}`}>
                          {user.status || 'Active'}
                        </span>
                      </td>
                      <td>
                        <div className="action-btns">
                          <button className="btn-icon">💬</button>
                          <button className="btn-icon warning" onClick={() => updateUserStatus(user.id, 'Flagged')}>⚠️</button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </section>
      </div>

      <style jsx>{`
        .dashboard-container { display: flex; min-height: 100vh; background: var(--bg-deep); }
        .main-content { margin-left: 280px; flex: 1; padding: 3rem; }
        .content-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 3rem; }

        .search-box {
          display: flex;
          align-items: center;
          padding: 0.75rem 1.25rem;
          gap: 0.75rem;
          min-width: 350px;
        }
        .search-icon { font-size: 1.1rem; }
        .search-box input {
          background: none;
          border: none;
          color: white;
          outline: none;
          font-size: 0.9rem;
          width: 100%;
        }

        .user-info-cell { display: flex; align-items: center; gap: 1rem; }
        .avatar-med {
          width: 40px;
          height: 40px;
          background: linear-gradient(135deg, var(--brand-secondary), var(--status-info));
          border-radius: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
          font-weight: 700;
          color: white;
        }

        .user-name { font-weight: 600; font-size: 0.95rem; }
        .user-id { font-size: 0.75rem; color: var(--text-dim); }

        .trust-meter { display: flex; align-items: center; gap: 1rem; min-width: 150px; }
        .meter-bg { flex: 1; height: 6px; background: var(--bg-surface-elevated); border-radius: 3px; overflow: hidden; }
        .meter-fill { height: 100%; border-radius: 3px; transition: width 1s ease-out; }
        .meter-fill.high { background: var(--status-success); box-shadow: 0 0 10px rgba(0, 230, 118, 0.4); }
        .meter-fill.mid { background: var(--status-warning); }
        .meter-fill.low { background: var(--status-danger); }
        .trust-value { font-size: 0.8rem; font-weight: 700; color: var(--text-muted); }

        .contribution-pill {
          background: var(--bg-surface-elevated);
          padding: 0.35rem 0.75rem;
          border-radius: 8px;
          font-size: 0.75rem;
          color: var(--text-muted);
          border: 1px solid var(--glass-border);
        }

        .status-pill {
          padding: 4px 10px;
          border-radius: 6px;
          font-size: 0.7rem;
          font-weight: 700;
          text-transform: uppercase;
        }
        .status-pill.active { background: rgba(0, 230, 118, 0.1); color: var(--status-success); }
        .status-pill.flagged { background: rgba(255, 234, 0, 0.1); color: var(--status-warning); }
        .status-pill.suspended { background: rgba(255, 23, 68, 0.1); color: var(--status-danger); }

        .action-btns { display: flex; gap: 0.5rem; }
        .btn-icon {
          background: var(--bg-surface-elevated);
          border: 1px solid var(--glass-border);
          width: 32px;
          height: 32px;
          border-radius: 8px;
          cursor: pointer;
          color: white;
          display: flex;
          align-items: center;
          justify-content: center;
          transition: var(--transition-fast);
        }
        .btn-icon:hover { border-color: var(--brand-primary); transform: scale(1.1); }
        .btn-icon.warning:hover { border-color: var(--status-warning); background: rgba(255, 234, 0, 0.1); }

        .custom-table { width: 100%; border-collapse: collapse; }
        .custom-table th { text-align: left; color: var(--text-dim); font-size: 0.75rem; text-transform: uppercase; padding: 1.5rem 1rem; }
        .custom-table td { padding: 1.25rem 1rem; border-top: 1px solid var(--glass-border); vertical-align: middle; font-size: 0.9rem; }
      `}</style>
    </main>
  );
}
