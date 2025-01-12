(ns yes-she-codes.study.week2.model.cartao
  (:import (java.time YearMonth)))

(defrecord Cartao [^Long       id
                   ^Long       numero
                   ^Long       cvv
                   ^YearMonth  validade
                   ^BigDecimal limite
                   ^String     cliente])