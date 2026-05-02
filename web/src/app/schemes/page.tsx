"use client";

import Sidebar from "@/components/Sidebar";
import { useState, useEffect } from "react";
import { FirebaseService } from "@/services/firebaseService";

export default function SchemesPage() {
  const [schemes, setSchemes] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [isAdding, setIsAdding] = useState(false);
  const [newScheme, setNewScheme] = useState({
    title: "", category: "Agriculture", beneficiaries: "", link: ""
  });

  useEffect(() => {
    const unsubscribe = FirebaseService.observeSchemes((data) => {
      setSchemes(data);
      setLoading(false);
    });
    return () => unsubscribe();
  }, []);

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await FirebaseService.addScheme(newScheme);
      setIsAdding(false);
      setNewScheme({ title: "", category: "Agriculture", beneficiaries: "", link: "" });
    } catch (error) {
      console.error("Save failed", error);
    }
  };

  const handleDelete = async (id: string) => {
    if (window.confirm("Are you sure you want to delete this scheme?")) {
      await FirebaseService.deleteScheme(id);
    }
  };

  return (
    <main className="dashboard-container">
      <Sidebar />
      
      <div className="main-content">
        <header className="content-header">
          <div>
            <h1 className="heading-xl">Governance Hub</h1>
            <p className="text-small">Curate and update government schemes for community awareness.</p>
          </div>
          <button className="btn-primary" onClick={() => setIsAdding(true)}>+ Add New Scheme</button>
        </header>

        <section className="schemes-grid">
          {schemes.map((scheme) => (
            <div key={scheme.id} className="glass-card scheme-card">
              <div className="scheme-badge">{scheme.category}</div>
              <h3 className="scheme-title">{scheme.title}</h3>
              <div className="scheme-info">
                <div className="info-item">
                  <span className="label">Target</span>
                  <span className="value">{scheme.beneficiaries}</span>
                </div>
                <div className="info-item">
                  <span className="label">Portal</span>
                  <span className="value link">{scheme.link}</span>
                </div>
              </div>
              <div className="scheme-actions">
                <button className="btn-secondary">Edit</button>
                <button className="btn-outline-danger" onClick={() => handleDelete(scheme.id)}>Delete</button>
              </div>
            </div>
          ))}
        </section>

        {isAdding && (
          <div className="modal-overlay">
            <div className="glass-card modal-content animate-fade-in">
              <div className="modal-header">
                <h2 className="heading-md">Add New Scheme</h2>
                <button className="close-btn" onClick={() => setIsAdding(false)}>✕</button>
              </div>
              <form className="modal-form" onSubmit={handleSave}>
                <div className="input-group">
                  <label>Scheme Title</label>
                  <input 
                    type="text" 
                    placeholder="e.g. PM-Kisan Samman Nidhi" 
                    value={newScheme.title}
                    onChange={(e) => setNewScheme({...newScheme, title: e.target.value})}
                    required
                  />
                </div>
                <div className="input-row">
                  <div className="input-group">
                    <label>Category</label>
                    <select 
                      value={newScheme.category}
                      onChange={(e) => setNewScheme({...newScheme, category: e.target.value})}
                    >
                      <option>Agriculture</option>
                      <option>Health</option>
                      <option>Education</option>
                      <option>Finance</option>
                    </select>
                  </div>
                  <div className="input-group">
                    <label>Beneficiaries</label>
                    <input 
                      type="text" 
                      placeholder="e.g. Farmers" 
                      value={newScheme.beneficiaries}
                      onChange={(e) => setNewScheme({...newScheme, beneficiaries: e.target.value})}
                      required
                    />
                  </div>
                </div>
                <div className="input-group">
                  <label>Official Portal Link</label>
                  <input 
                    type="text" 
                    placeholder="https://..." 
                    value={newScheme.link}
                    onChange={(e) => setNewScheme({...newScheme, link: e.target.value})}
                    required
                  />
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn-ghost" onClick={() => setIsAdding(false)}>Cancel</button>
                  <button type="submit" className="btn-primary">Save Scheme</button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>

      <style jsx>{`
        .dashboard-container { display: flex; min-height: 100vh; background: var(--bg-deep); }
        .main-content { margin-left: 280px; flex: 1; padding: 3rem; }
        .content-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 3rem; }

        .schemes-grid {
          display: grid;
          grid-template-columns: repeat(auto-fill, minmax(340px, 1fr));
          gap: 2rem;
        }

        .scheme-card {
          padding: 2rem;
          display: flex;
          flex-direction: column;
          gap: 1.5rem;
          transition: var(--transition-fast);
        }

        .scheme-card:hover { border-color: var(--brand-primary); transform: translateY(-4px); }

        .scheme-badge {
          background: rgba(255, 140, 0, 0.1);
          color: var(--brand-primary);
          padding: 0.25rem 0.75rem;
          border-radius: 6px;
          font-size: 0.7rem;
          font-weight: 700;
          text-transform: uppercase;
          width: fit-content;
        }

        .scheme-title { font-size: 1.25rem; font-weight: 700; }

        .scheme-info { display: flex; flex-direction: column; gap: 1rem; }
        .info-item { display: flex; justify-content: space-between; align-items: center; }
        .info-item .label { font-size: 0.75rem; color: var(--text-dim); }
        .info-item .value { font-size: 0.875rem; font-weight: 500; }
        .info-item .value.link { color: var(--brand-secondary); text-decoration: underline; cursor: pointer; }

        .scheme-actions {
          display: grid;
          grid-template-columns: 1fr 1fr;
          gap: 1rem;
          margin-top: 0.5rem;
        }

        .btn-secondary {
          background: var(--bg-surface-elevated);
          border: 1px solid var(--glass-border);
          color: white;
          padding: 0.6rem;
          border-radius: 8px;
          cursor: pointer;
          font-weight: 600;
        }

        .btn-outline-danger {
          background: none;
          border: 1px solid rgba(255, 23, 68, 0.3);
          color: var(--status-danger);
          padding: 0.6rem;
          border-radius: 8px;
          cursor: pointer;
          font-weight: 600;
        }

        .modal-overlay {
          position: fixed;
          top: 0; left: 0; right: 0; bottom: 0;
          background: rgba(0, 0, 0, 0.8);
          backdrop-filter: blur(8px);
          display: flex;
          align-items: center;
          justify-content: center;
          z-index: 1000;
        }

        .modal-content {
          width: 100%;
          max-width: 600px;
          background: var(--bg-surface);
          border: 1px solid var(--glass-border);
        }

        .modal-header {
          padding: 1.5rem;
          border-bottom: 1px solid var(--glass-border);
          display: flex;
          justify-content: space-between;
          align-items: center;
        }

        .modal-form { padding: 2rem; display: flex; flex-direction: column; gap: 1.5rem; }
        .input-group { display: flex; flex-direction: column; gap: 0.5rem; }
        .input-group label { font-size: 0.75rem; color: var(--text-dim); text-transform: uppercase; font-weight: 600; }
        .input-group input, .input-group select {
          background: var(--bg-deep);
          border: 1px solid var(--glass-border);
          padding: 0.8rem;
          border-radius: 8px;
          color: white;
          outline: none;
        }

        .input-row { display: grid; grid-template-columns: 1fr 1fr; gap: 1.5rem; }

        .modal-footer {
          display: flex;
          justify-content: flex-end;
          gap: 1rem;
          margin-top: 1rem;
        }

        .btn-ghost { background: none; border: none; color: var(--text-muted); cursor: pointer; font-weight: 600; }
        .btn-primary {
          background: linear-gradient(135deg, var(--brand-primary), var(--brand-primary-light));
          border: none; padding: 0.8rem 1.5rem; border-radius: 10px; color: white; font-weight: 700; cursor: pointer;
        }
      `}</style>
    </main>
  );
}
