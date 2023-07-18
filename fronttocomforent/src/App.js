import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CustomAppBar from './components/AppBar';
import RegistrationForm from './components/RegistrationForm';
import CreateHousingPage from './components/CreateHousingPage';
import LoginPage from './components/LoginPage';

function App() {
  return (
    <Router>
      <div className="App">
        <CustomAppBar />
        <Routes>
          <Route path="/registration" element={<RegistrationForm />} />
          <Route path="/rent out" element={<CreateHousingPage />} />
          <Route path="/login" element={<LoginPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
