import { useEffect, useState } from "react";
import api from "../services/api";

export default function ActivityList() {
  const [atividades, setAtividades] = useState([]);

  useEffect(() => {
    api.get("/atividades")
      .then((res) => setAtividades(res.data))
      .catch((err) => console.error("Erro ao buscar atividades:", err));
  }, []);

  return (
    <div>
      <h2>Lista de Atividades</h2>
      {atividades.length === 0 ? (
        <p>Nenhuma atividade cadastrada.</p>
      ) : (
        <ul>
          {atividades.map((a) => (
            <li key={a.id}>
              <strong>{a.titulo}</strong> — {a.contexto} ({a.energiaNecessaria})
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
