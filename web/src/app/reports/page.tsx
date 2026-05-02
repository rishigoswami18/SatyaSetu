"use client";

import Sidebar from "@/components/Sidebar";
import { useState, useEffect } from "react";
import { FirebaseService } from "@/services/firebaseService";

// Real-time data will be fetched from Firebase


export default function ReportsPage() {
  const [reports, setReports] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedReport, setSelectedReport] = useState<any | null>(null);

  useEffect(() => {
    const unsubscribe = FirebaseService.observePendingReports((data) => {
      setReports(data);
      setLoading(false);
    });
    return () => unsubscribe();
  }, []);

  const handleAction = async (id: string, newStatus: string) => {
    try {
      if (newStatus === 'Verified') {
        await FirebaseService.approveReport(id, "Verified by Admin");
      } else {
        await FirebaseService.rejectReport(id, "Rejected by Admin");
      }
      setSelectedReport(null);
    } catch (error) {
      console.error("Action failed", error);
    }
  };

  return (
    <main className="dashboard-container">
      <Sidebar />
      
      <div className="main-content">
        <header className="content-header">
          <div>
            <h1 className="heading-xl">Truth Verification</h1>
            <p className="text-small">Review and verify community reports to maintain platform integrity.</p>
          </div>
          <div className="filter-group">
            <select className="glass-select">
              <option>All Categories</option>
              <option>Fraud</option>
              <option>Superstition</option>
            </select>
          </div>
        </header>

        <section className="reports-grid">
          <div className="glass-card table-section">
            <div className="table-wrapper">
              <table className="custom-table">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Report Detail</th>
                    <th>Village</th>
                    <th>Severity</th>
                    <th>Status</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {reports.map((report) => (
                    <tr key={report.id} onClick={() => setSelectedReport(report)} className="clickable-row">
                      <td className="text-dim">{report.id}</td>
                      <td>
                        <div className="report-info">
                          <p className="report-title">{report.title}</p>
                          <p className="report-reporter">By {report.reporter}</p>
                        </div>
                      </td>
                      <td>{report.village}</td>
                      <td>
                        <span className={`severity-badge ${report.severity.toLowerCase()}`}>
                          {report.severity}
                        </span>
                      </td>
                      <td>
                        <span className={`status-tag ${report.status.toLowerCase().replace(' ', '-')}`}>
                          {report.status}
                        </span>
                      </td>
                      <td><button className="btn-icon">➡️</button></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          {selectedReport && (
            <div className="glass-card detail-panel animate-fade-in">
              <div className="panel-header">
                <h2 className="heading-md">Report Analysis</h2>
                <button className="close-btn" onClick={() => setSelectedReport(null)}>✕</button>
              </div>
              <div className="panel-body">
                <div className="detail-item">
                  <label>Title</label>
                  <p>{selectedReport.title}</p>
                </div>
                <div className="detail-item">
                  <label>Description</label>
                  <p className="description-text">{selectedReport.description}</p>
                </div>
                <div className="detail-row">
                  <div className="detail-item">
                    <label>Reported By</label>
                    <p>{selectedReport.reporter}</p>
                  </div>
                  <div className="detail-item">
                    <label>Village</label>
                    <p>{selectedReport.village}</p>
                  </div>
                </div>
                
                <div className="action-footer">
                  <button 
                    className="btn-danger" 
                    onClick={() => handleAction(selectedReport.id, 'Rejected')}
                  >
                    Reject Report
                  </button>
                  <button 
                    className="btn-success"
                    onClick={() => handleAction(selectedReport.id, 'Verified')}
                  >
                    Verify & Alert Community
                  </button>
                </div>
              </div>
            </div>
          )}
        </section>
      </div>

      <style jsx>{`
        .dashboard-container { display: flex; min-height: 100vh; background: var(--bg-deep); }
        .main-content { margin-left: 280px; flex: 1; padding: 3rem; }
        .content-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 2.5rem; }
        
        .glass-select {
          background: var(--bg-surface-elevated);
          border: 1px solid var(--glass-border);
          color: white;
          padding: 0.5rem 1rem;
          border-radius: 8px;
          outline: none;
        }

        .reports-grid {
          display: grid;
          grid-template-columns: ${selectedReport ? '1fr 400px' : '1fr'};
          gap: 2rem;
          transition: all 0.3s ease;
        }

        .clickable-row { cursor: pointer; transition: var(--transition-fast); }
        .clickable-row:hover { background: var(--glass-bg); }

        .report-title { font-weight: 600; margin-bottom: 2px; }
        .report-reporter { font-size: 0.75rem; color: var(--text-dim); }

        .severity-badge {
          padding: 2px 8px;
          border-radius: 4px;
          font-size: 0.7rem;
          font-weight: 800;
          text-transform: uppercase;
        }
        .severity-badge.critical { background: rgba(255, 23, 68, 0.2); color: #ff1744; }
        .severity-badge.high { background: rgba(255, 140, 0, 0.2); color: #ff8c00; }
        .severity-badge.medium { background: rgba(41, 121, 255, 0.2); color: #2979ff; }

        .status-tag {
          font-size: 0.75rem;
          font-weight: 600;
          color: var(--text-muted);
        }
        .status-tag.verified { color: var(--status-success); }
        .status-tag.rejected { color: var(--status-danger); }

        .detail-panel {
          height: fit-content;
          position: sticky;
          top: 3rem;
        }

        .panel-header {
          padding: 1.5rem;
          border-bottom: 1px solid var(--glass-border);
          display: flex;
          justify-content: space-between;
          align-items: center;
        }

        .close-btn { background: none; border: none; color: var(--text-dim); cursor: pointer; font-size: 1.2rem; }

        .panel-body { padding: 1.5rem; display: flex; flex-direction: column; gap: 1.5rem; }
        .detail-item label { display: block; font-size: 0.7rem; text-transform: uppercase; color: var(--text-dim); margin-bottom: 4px; letter-spacing: 0.05em; }
        .detail-item p { font-size: 0.95rem; font-weight: 500; }
        .description-text { line-height: 1.6; color: var(--text-muted); font-size: 0.875rem !important; }
        .detail-row { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }

        .action-footer {
          margin-top: 1rem;
          display: grid;
          grid-template-columns: 1fr;
          gap: 0.75rem;
        }

        .btn-success {
          background: var(--status-success);
          color: #0a0b10;
          border: none;
          padding: 0.875rem;
          border-radius: 12px;
          font-weight: 700;
          cursor: pointer;
          transition: var(--transition-fast);
        }

        .btn-danger {
          background: rgba(255, 23, 68, 0.1);
          color: var(--status-danger);
          border: 1px solid var(--status-danger);
          padding: 0.875rem;
          border-radius: 12px;
          font-weight: 600;
          cursor: pointer;
          transition: var(--transition-fast);
        }

        .btn-success:hover { filter: brightness(1.1); transform: translateY(-2px); }
        .btn-danger:hover { background: rgba(255, 23, 68, 0.2); }

        .custom-table { width: 100%; border-collapse: collapse; }
        .custom-table th { text-align: left; color: var(--text-dim); font-size: 0.75rem; text-transform: uppercase; padding: 1rem; }
        .custom-table td { padding: 1.25rem 1rem; border-top: 1px solid var(--glass-border); font-size: 0.875rem; }
        .btn-icon { background: var(--bg-surface-elevated); border: 1px solid var(--glass-border); width: 32px; height: 32px; border-radius: 8px; cursor: pointer; color: white; }
      `}</style>
    </main>
  );
}
