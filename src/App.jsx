import { useState } from "react";
import ActivityList from "./components/ActivityList";
import ActivityForm from "./components/ActivityForm";

export default function App() {
  const [atividades, setAtividades] = useState([]);

  const handleAdd = (nova) => {
    setAtividades([...atividades, nova]);
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>To-Do List - Treina Recife</h1>
      <ActivityForm onAdd={handleAdd} />
      <ActivityList atividades={atividades} />
    </div>
  );
}
