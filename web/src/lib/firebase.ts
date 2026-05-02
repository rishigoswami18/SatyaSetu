import { initializeApp, getApps, getApp } from "firebase/app";
import { getDatabase } from "firebase/database";
import { getStorage } from "firebase/storage";
import { getAuth } from "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyBcOI-78CI7ab2odJseK7LJZyQhLwaZMjE",
  authDomain: "sochbadlo-7c117.firebaseapp.com",
  databaseURL: "https://sochbadlo-7c117-default-rtdb.firebaseio.com",
  projectId: "sochbadlo-7c117",
  storageBucket: "sochbadlo-7c117.firebasestorage.app",
  messagingSenderId: "1075815167586",
  appId: "1:1075815167586:web:e85eb8e39644408504833b" // Placeholder web app ID
};

// Initialize Firebase
const app = getApps().length > 0 ? getApp() : initializeApp(firebaseConfig);
const db = getDatabase(app);
const storage = getStorage(app);
const auth = getAuth(app);

export { db, storage, auth };
