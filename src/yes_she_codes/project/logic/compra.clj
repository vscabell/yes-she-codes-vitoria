(ns yes-she-codes.project.logic.compra
  (:require [schema.core :as s]
            [yes-she-codes.project.model.cartao :as model.cartao]
            [yes-she-codes.project.model.compra :as model.compra])
  (:import (java.time Month)))

(s/defn total-gasto :- model.cartao/ValorFinanceiro
  [lista-compras :- [model.compra/Compra]]
  (transduce (map :compra/valor) + 0M lista-compras))

(s/defn lista-de-compras-do-mes :- [model.compra/Compra]
  [mes :- Month
   lista-compras :- [model.compra/Compra]]
  (->> lista-compras
       (filter #(= mes (.getMonth (:compra/data %))))))

(s/defn lista-de-compras-do-estabelecimento :- [model.compra/Compra]
  [estabelecimento :- model.compra/Estabelecimento
   lista-compras :- [model.compra/Compra]]
  (->> lista-compras
       (filter #(= estabelecimento (:compra/estabelecimento %)))))

(s/defn total-gasto-no-mes :- model.cartao/ValorFinanceiro
  [mes :- Month
   lista-compras :- [model.compra/Compra]]
  (->> lista-compras
       (lista-de-compras-do-mes mes)
       (total-gasto)))

(s/defn lista-de-compras-por-intervalo-de-valores :- [model.compra/Compra]
  [valor-max :- model.cartao/ValorFinanceiro
   valor-min :- model.cartao/ValorFinanceiro
   lista-compras :- [model.compra/Compra]]
  (->> lista-compras
       (filter #(<= valor-min (:compra/valor %) valor-max))))

(s/defn gasto-por-categoria :- model.compra/GastoCategoria
  [lista-compras :- [model.compra/Compra]]
  (->> lista-compras
       (group-by :compra/categoria)
       (map (fn [[key vals]] [key (total-gasto vals)]))
       (reduce conj {})))