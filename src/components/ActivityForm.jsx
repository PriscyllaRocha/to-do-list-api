import { useState } from "react";
import api from "../services/api";

export default function ActivityForm({ onAdd }) {
  const [titulo, setTitulo] = useState("");
  const [contexto, setContexto] = useState("");
  const [energia, setEnergia] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const nova = {
      titulo,
      status: { id: 1 },
      responsavel: { id: 1 },
      duracaoEstimadaMin: 30,
      contexto,
      energiaNecessaria: Number(energia),
    };

    try {
      const res = await api.post("/atividades", nova);
      onAdd(res.data);
      setTitulo("");
      setContexto("");
      setEnergia("");
    } catch (err) {
      console.error("Erro ao criar atividade:", err);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Criar nova atividade</h2>
      <input
        type="text"
        placeholder="Título"
        value={titulo}
        onChange={(e) => setTitulo(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Contexto (ex: Em Casa)"
        value={contexto}
        onChange={(e) => setContexto(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="Energia necessária"
        value={energia}
        onChange={(e) => setEnergia(e.target.value)}
        required
      />
      <button type="submit">Adicionar</button>
    </form>
  );
}
