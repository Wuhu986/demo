import { useState } from 'react';
import axios from 'axios';

function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const login = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('/api/login', null, { params: { username, password } });
      setMessage(res.data.message);
    } catch { setMessage('登录失败'); }
  };

  return (
    <div>
      <h2>React 登录</h2>
      <form onSubmit={login}>
        <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="用户名" />
        <input value={password} onChange={(e) => setPassword(e.target.value)} type="password" placeholder="密码" />
        <button type="submit">登录</button>
      </form>
      <p>{message}</p>
    </div>
  );
}

export default App;