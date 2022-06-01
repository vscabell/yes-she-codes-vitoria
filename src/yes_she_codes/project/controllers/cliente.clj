(ns yes-she-codes.project.controllers.cliente
  (:require [schema.core :as s]
            [yes-she-codes.project.model.cliente :as model.cliente]
            [yes-she-codes.project.logic.common.common :as logic.common]))

(s/defn insere-cliente! :- [model.cliente/Cliente]
  [clientes :- [model.cliente/Cliente]
   registro :- model.cliente/Cliente]
  (swap! clientes logic.common/insere-registro registro))

(s/defn lista-clientes!
  [clientes :- [model.cliente/Cliente]]
  (logic.common/lista-entidade @clientes))

(s/defn pesquisa-cliente-por-id! :- model.cliente/Cliente
  [clientes :- [model.cliente/Cliente]
   id :- s/Num]
  (logic.common/pesquisa-registro-por-id @clientes id))

(s/defn exclui-cliente! :- [model.cliente/Cliente]
  [clientes :- [model.cliente/Cliente]
   id :- s/NUm]
  (swap! clientes logic.common/exclui-registro id))
