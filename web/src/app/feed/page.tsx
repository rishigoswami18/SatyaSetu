"use client";

import Sidebar from "@/components/Sidebar";
import { useState, useEffect } from "react";
import { FirebaseService } from "@/services/firebaseService";

export default function FeedPage() {
  const [posts, setPosts] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const unsubscribe = FirebaseService.observePosts((data) => {
      setPosts(data);
      setLoading(false);
    });
    return () => unsubscribe();
  }, []);

  const deletePost = async (id: string) => {
    if (window.confirm("Are you sure you want to remove this community post?")) {
      await FirebaseService.deletePost(id);
    }
  };

  return (
    <main className="dashboard-container">
      <Sidebar />
      
      <div className="main-content">
        <header className="content-header">
          <div>
            <h1 className="heading-xl">Village Feed Moderator</h1>
            <p className="text-small">Monitor and moderate community conversations to ensure a safe environment.</p>
          </div>
          <div className="stats-mini">
            <span className="stat-pill">Active Posts: {posts.length}</span>
            <span className="stat-pill warning">Flagged: {posts.filter(p => p.flags > 0).length}</span>
          </div>
        </header>

        <section className="feed-management">
          <div className="glass-card">
            <div className="table-wrapper">
              <table className="custom-table">
                <thead>
                  <tr>
                    <th>Author & Village</th>
                    <th>Content Snippet</th>
                    <th>Engagement</th>
                    <th>Moderation</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {posts.map((post) => (
                    <tr key={post.id} className={post.flagCount > 5 ? 'flagged-row' : ''}>
                      <td>
                        <div className="author-cell">
                          <div className="avatar-sm">{(post.userName || 'U')[0]}</div>
                          <div>
                            <p className="author-name">{post.userName || 'Anonymous'}</p>
                            <p className="village-name">{post.villageId}</p>
                          </div>
                        </div>
                      </td>
                      <td>
                        <p className="content-snippet">{post.description}</p>
                        <span className="post-date">{new Date(post.timestamp).toLocaleDateString()}</span>
                      </td>
                      <td>
                        <div className="engagement-cell">
                          <span>❤️ {post.likes || 0}</span>
                          <span className={(post.flagCount || 0) > 0 ? 'flags-count alert' : 'flags-count'}>🚩 {post.flagCount || 0}</span>
                        </div>
                      </td>
                      <td>
                        <span className={`mod-badge ${(post.flagCount || 0) > 5 ? 'danger' : (post.flagCount || 0) > 0 ? 'warning' : 'safe'}`}>
                          {(post.flagCount || 0) > 5 ? 'Requires Action' : (post.flagCount || 0) > 0 ? 'Review Needed' : 'Safe'}
                        </span>
                      </td>
                      <td>
                        <div className="action-btns">
                          <button className="btn-icon">👁️</button>
                          <button className="btn-icon delete" onClick={() => deletePost(post.id)}>🗑️</button>
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

        .stats-mini { display: flex; gap: 1rem; }
        .stat-pill { background: var(--bg-surface-elevated); padding: 0.5rem 1rem; border-radius: 20px; font-size: 0.75rem; font-weight: 600; color: var(--text-muted); border: 1px solid var(--glass-border); }
        .stat-pill.warning { border-color: var(--status-danger); color: var(--status-danger); }

        .author-cell { display: flex; align-items: center; gap: 1rem; }
        .avatar-sm { width: 32px; height: 32px; background: var(--brand-primary); border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 0.75rem; font-weight: 800; }
        .author-name { font-size: 0.875rem; font-weight: 600; }
        .village-name { font-size: 0.7rem; color: var(--text-dim); }

        .content-snippet { font-size: 0.875rem; color: var(--text-main); margin-bottom: 4px; max-width: 300px; }
        .post-date { font-size: 0.7rem; color: var(--text-dim); }

        .engagement-cell { display: flex; gap: 1rem; font-size: 0.8rem; font-weight: 600; }
        .flags-count.alert { color: var(--status-danger); }

        .mod-badge { padding: 4px 10px; border-radius: 6px; font-size: 0.7rem; font-weight: 700; text-transform: uppercase; }
        .mod-badge.safe { background: rgba(0, 230, 118, 0.1); color: var(--status-success); }
        .mod-badge.warning { background: rgba(255, 234, 0, 0.1); color: var(--status-warning); }
        .mod-badge.danger { background: rgba(255, 23, 68, 0.1); color: var(--status-danger); }

        .flagged-row { background: rgba(255, 23, 68, 0.05); }

        .action-btns { display: flex; gap: 0.5rem; }
        .btn-icon.delete:hover { background: var(--status-danger); border-color: var(--status-danger); }

        .custom-table { width: 100%; border-collapse: collapse; }
        .custom-table th { text-align: left; color: var(--text-dim); font-size: 0.75rem; text-transform: uppercase; padding: 1.5rem 1rem; }
        .custom-table td { padding: 1.5rem 1rem; border-top: 1px solid var(--glass-border); vertical-align: middle; }
        .btn-icon { background: var(--bg-surface-elevated); border: 1px solid var(--glass-border); width: 36px; height: 36px; border-radius: 10px; cursor: pointer; color: white; transition: var(--transition-fast); }
        .btn-icon:hover { border-color: var(--brand-primary); transform: scale(1.1); }
      `}</style>
    </main>
  );
}
