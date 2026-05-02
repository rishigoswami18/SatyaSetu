import { db } from "@/lib/firebase";
import { ref, onValue, set, update, push, remove, query, orderByChild, equalTo, get } from "firebase/database";

// Node Names matching Android Constants
const NODES = {
  REPORTS: "pending_reports",
  APPROVED_REPORTS: "approved_reports",
  SCHEMES: "schemes",
  POSTS: "posts",
  USERS: "users",
  VILLAGES: "villages"
};

export const FirebaseService = {
  // --- Reports Management ---
  observePendingReports: (callback: (reports: any[]) => void) => {
    const reportsRef = ref(db, NODES.REPORTS);
    return onValue(reportsRef, (snapshot) => {
      const data = snapshot.val();
      if (data) {
        const list = Object.keys(data).map(key => ({
          ...data[key],
          id: key
        })).filter(r => r.status === 'PENDING');
        callback(list);
      } else {
        callback([]);
      }
    });
  },

  approveReport: async (reportId: string, adminNote: string) => {
    const reportRef = ref(db, `${NODES.REPORTS}/${reportId}`);
    const snapshot = await get(reportRef);
    const report = snapshot.val();

    if (report) {
      const approvedReport = {
        ...report,
        status: 'APPROVED',
        adminNote,
        reviewedAt: Date.now(),
        reviewedBy: 'Admin'
      };

      // 1. Add to approved_reports
      await set(ref(db, `${NODES.APPROVED_REPORTS}/${reportId}`), approvedReport);
      // 2. Update status in pending_reports
      await update(reportRef, { status: 'APPROVED', adminNote });
    }
  },

  rejectReport: async (reportId: string, adminNote: string) => {
    const reportRef = ref(db, `${NODES.REPORTS}/${reportId}`);
    await update(reportRef, {
      status: 'REJECTED',
      adminNote,
      reviewedAt: Date.now(),
      reviewedBy: 'Admin'
    });
  },

  // --- Schemes Management ---
  observeSchemes: (callback: (schemes: any[]) => void) => {
    const schemesRef = ref(db, NODES.SCHEMES);
    return onValue(schemesRef, (snapshot) => {
      const data = snapshot.val();
      if (data) {
        const list = Object.keys(data).map(key => ({
          ...data[key],
          id: key
        }));
        callback(list);
      } else {
        callback([]);
      }
    });
  },

  addScheme: async (scheme: any) => {
    const schemesRef = ref(db, NODES.SCHEMES);
    const newSchemeRef = push(schemesRef);
    await set(newSchemeRef, { ...scheme, createdAt: Date.now() });
  },

  deleteScheme: async (id: string) => {
    await remove(ref(db, `${NODES.SCHEMES}/${id}`));
  },

  // --- Village Feed Management ---
  observePosts: (callback: (posts: any[]) => void) => {
    const postsRef = ref(db, NODES.POSTS);
    return onValue(postsRef, (snapshot) => {
      const data = snapshot.val();
      if (data) {
        const list = Object.keys(data).map(key => ({
          ...data[key],
          id: key
        }));
        callback(list);
      } else {
        callback([]);
      }
    });
  },

  deletePost: async (id: string) => {
    await remove(ref(db, `${NODES.POSTS}/${id}`));
  },

  // --- User Management ---
  observeUsers: (callback: (users: any[]) => void) => {
    const usersRef = ref(db, NODES.USERS);
    return onValue(usersRef, (snapshot) => {
      const data = snapshot.val();
      if (data) {
        const list = Object.keys(data).map(key => ({
          ...data[key],
          id: key
        }));
        callback(list);
      } else {
        callback([]);
      }
    });
  },

  updateUserStatus: async (userId: string, status: string) => {
    await update(ref(db, `${NODES.USERS}/${userId}`), { status });
  }
};
