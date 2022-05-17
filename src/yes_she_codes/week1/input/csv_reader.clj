(ns yes-she-codes.week1.input.csv-reader
  (:require [yes-she-codes.week1.adapter.adapter :as a]))

(defn lista-clientes []
  (a/dado-bruto->model
    "data/clientes.csv"
    a/criar-cliente))


(defn lista-cartoes []
  (a/dado-bruto->model
    "data/cartoes.csv"
    a/criar-cartao))


(defn lista-compras []
  (a/dado-bruto->model
    "data/compras.csv"
    a/criar-compra))