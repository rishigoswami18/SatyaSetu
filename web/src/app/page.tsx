"use client";

import Sidebar from "@/components/Sidebar";

export default function Home() {
  const stats = [
    { label: 'Community Members', value: '1,284', change: '+12%', icon: '👥', color: 'var(--brand-secondary)' },
    { label: 'Pending Reports', value: '14', change: '-2', icon: '🛡️', color: 'var(--status-danger)' },
    { label: 'Village Posts', value: '458', change: '+84', icon: '🧑‍🌾', color: 'var(--brand-primary)' },
    { label: 'Govt Schemes', value: '124', change: '+3', icon: '📋', color: 'var(--status-success)' },
  ];

  const recentReports = [
    { id: '1', title: 'Fake Baba in Sitapur', category: 'Superstition', status: 'Pending', time: '2h ago' },
    { id: '2', title: 'Fake PM-Kisan Website', category: 'Fraud', status: 'In Review', time: '5h ago' },
    { id: '3', title: 'Scholarship Scam Alert', category: 'Education', status: 'Pending', time: '1d ago' },
  ];

  return (
    <main className="dashboard-container">
      <Sidebar />
      
      <div className="main-content">
        <header className="content-header">
          <div>
            <h1 className="heading-xl">Insights Engine</h1>
            <p className="text-small">Real-time overview of the SatyaSetu community ecosystem.</p>
          </div>
          <button className="btn-primary">Generate Report</button>
        </header>

        <section className="stats-grid">
          {stats.map((stat) => (
            <div key={stat.label} className="glass-card stat-card">
              <div className="stat-header">
                <span className="stat-icon" style={{ background: `${stat.color}20`, color: stat.color }}>
                  {stat.icon}
                </span>
                <span className={`stat-change ${stat.change.startsWith('+') ? 'up' : 'down'}`}>
                  {stat.change}
                </span>
              </div>
              <div className="stat-body">
                <h3 className="stat-value">{stat.value}</h3>
                <p className="stat-label">{stat.label}</p>
              </div>
            </div>
          ))}
        </section>

        <section className="dashboard-sections">
          <div className="glass-card table-section">
            <div className="section-header">
              <h2 className="heading-md">Truth Verification Queue</h2>
              <button className="text-btn">View All</button>
            </div>
            <div className="table-wrapper">
              <table className="custom-table">
                <thead>
                  <tr>
                    <th>Report Detail</th>
                    <th>Category</th>
                    <th>Status</th>
                    <th>Timeline</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {recentReports.map((report) => (
                    <tr key={report.id}>
                      <td>{report.title}</td>
                      <td><span className="badge-outline">{report.category}</span></td>
                      <td>
                        <span className={`status-dot ${report.status.toLowerCase().replace(' ', '-')}`}></span>
                        {report.status}
                      </td>
                      <td>{report.time}</td>
                      <td><button className="btn-icon">👁️</button></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

          <div className="glass-card trending-section">
            <div className="section-header">
              <h2 className="heading-md">Engagement Velocity</h2>
            </div>
            <div className="trending-list">
              {[1, 2, 3].map((i) => (
                <div key={i} className="trending-item">
                  <div className="trending-meta">
                    <span className="trend-rank">#{i}</span>
                    <p className="trend-text">Village road construction updates in Rampur...</p>
                  </div>
                  <span className="trend-score">🔥 {90 - i * 5}</span>
                </div>
              ))}
            </div>
          </div>
        </section>
      </div>

      <style jsx>{`
        .dashboard-container {
          display: flex;
          min-height: 100vh;
          background: var(--bg-deep);
        }

        .main-content {
          margin-left: 280px;
          flex: 1;
          padding: 3rem;
          max-width: 1600px;
        }

        .content-header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          margin-bottom: 3rem;
          animation: fadeIn 0.5s ease;
        }

        .btn-primary {
          background: linear-gradient(135deg, var(--brand-primary), var(--brand-primary-light));
          border: none;
          padding: 0.75rem 1.5rem;
          border-radius: 12px;
          color: white;
          font-weight: 600;
          cursor: pointer;
          transition: var(--transition-fast);
          box-shadow: 0 4px 12px rgba(255, 140, 0, 0.2);
        }

        .btn-primary:hover {
          transform: translateY(-2px);
          box-shadow: 0 6px 16px rgba(255, 140, 0, 0.3);
        }

        .stats-grid {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
          gap: 2rem;
          margin-bottom: 3rem;
        }

        .stat-card {
          padding: 1.5rem;
          transition: var(--transition-fast);
        }

        .stat-card:hover {
          border-color: var(--brand-primary);
          transform: translateY(-4px);
        }

        .stat-header {
          display: flex;
          justify-content: space-between;
          align-items: flex-start;
          margin-bottom: 1.5rem;
        }

        .stat-icon {
          width: 48px;
          height: 48px;
          border-radius: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 1.5rem;
        }

        .stat-change {
          font-size: 0.75rem;
          font-weight: 700;
          padding: 0.25rem 0.5rem;
          border-radius: 6px;
        }

        .stat-change.up { background: rgba(0, 230, 118, 0.1); color: var(--status-success); }
        .stat-change.down { background: rgba(255, 23, 68, 0.1); color: var(--status-danger); }

        .stat-value {
          font-size: 2rem;
          font-weight: 800;
          margin-bottom: 0.25rem;
        }

        .stat-label {
          color: var(--text-muted);
          font-size: 0.875rem;
          font-weight: 500;
        }

        .dashboard-sections {
          display: grid;
          grid-template-columns: 2fr 1fr;
          gap: 2rem;
        }

        .section-header {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 1.5rem;
          border-bottom: 1px solid var(--glass-border);
        }

        .text-btn {
          background: none;
          border: none;
          color: var(--brand-primary);
          font-weight: 600;
          cursor: pointer;
        }

        .table-wrapper {
          padding: 1rem;
        }

        .custom-table {
          width: 100%;
          border-collapse: collapse;
        }

        .custom-table th {
          text-align: left;
          color: var(--text-dim);
          font-size: 0.75rem;
          text-transform: uppercase;
          letter-spacing: 0.05em;
          padding: 1rem;
        }

        .custom-table td {
          padding: 1.25rem 1rem;
          border-top: 1px solid var(--glass-border);
          font-size: 0.875rem;
        }

        .badge-outline {
          padding: 0.25rem 0.75rem;
          border: 1px solid var(--glass-border);
          border-radius: 20px;
          font-size: 0.75rem;
          color: var(--text-muted);
        }

        .status-dot {
          display: inline-block;
          width: 8px;
          height: 8px;
          border-radius: 50%;
          margin-right: 8px;
        }

        .status-dot.pending { background: var(--status-warning); }
        .status-dot.in-review { background: var(--status-info); }

        .btn-icon {
          background: var(--bg-surface-elevated);
          border: 1px solid var(--glass-border);
          width: 32px;
          height: 32px;
          border-radius: 8px;
          cursor: pointer;
          color: white;
        }

        .trending-list {
          padding: 1rem;
        }

        .trending-item {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 1rem;
          border-radius: 12px;
          transition: var(--transition-fast);
        }

        .trending-item:hover {
          background: var(--glass-bg);
        }

        .trending-meta {
          display: flex;
          align-items: center;
          gap: 1rem;
        }

        .trend-rank {
          font-weight: 800;
          color: var(--text-dim);
          width: 24px;
        }

        .trend-text {
          font-size: 0.875rem;
          max-width: 200px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }

        .trend-score {
          font-size: 0.875rem;
          font-weight: 700;
          color: var(--brand-primary);
        }
      `}</style>
    </main>
  );
}
