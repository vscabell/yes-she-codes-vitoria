(ns yes-she-codes.study.week1.adapter.adapter-test
  (:require [clojure.test :as t]
            [yes-she-codes.study.week1.adapter.adapter :as a]))


(t/deftest criar-cliente-test
  (t/testing "criacao do cliente a partir dos dados em string"
    (let [input              ["fulano" "888.888.888-88" "fulano@fulano.com"]
          resultado-esperado {:cpf "888.888.888-88" :nome "fulano" :email "fulano@fulano.com"}]
      (t/is (= (a/criar-cliente input) resultado-esperado)))))


(t/deftest criar-cartao-test
  (t/testing "criacao do cartao a partir dos dados em string"
    (let [input              ["999 999 999" "999" "2022-01" "999.99" "999.999.999-99"]
          resultado-esperado {:cvv 999
                              :numero 999999999
                              :limite 999.99M
                              :validade nil
                              :cliente "999.999.999-99"}
          resultado          (a/criar-cartao input)
          resultado-sem-obj  (assoc resultado :validade nil)]
      (t/is (= resultado-sem-obj resultado-esperado)))))


(t/deftest criar-compra-test
  (t/testing "criacao da compra a partir dos dados em string"
    (let [input              ["2022-12-01" "99.99" "estabelecimento" "categoria" "999 999 999"]
          resultado-esperado {:cartao 999999999
                              :data nil
                              :estabelecimento "estabelecimento"
                              :categoria "categoria"
                              :valor 99.99M}
          resultado          (a/criar-compra input)
          resultado-sem-obj  (assoc resultado :data nil)]
      (t/is (= resultado-sem-obj resultado-esperado)))))


(t/deftest dado-bruto->model-test

  (t/testing "adapter dos dados dos clientes"
    (let [caminho-arquivo    "data/in/clientes.csv"
          fn-model           a/criar-cliente
          estrutura-esperada [{:nome "Feiticeira Escarlate", :cpf "000.111.222-33", :email "feiticeira.poderosa@vingadoras.com.br"}
                              {:nome "Viúva Negra", :cpf "333.444.555-66", :email "viuva.casca.grossa@vingadoras.com.br"}
                              {:nome "Hermione Granger", :cpf "666.777.888-99", :email "hermione.salvadora@hogwarts.com"}
                              {:nome "Daenerys Targaryen", :cpf "999.123.456-78", :email "mae.dos.dragoes@got.com"}]]
      (t/is (= (a/dado-bruto->model caminho-arquivo fn-model) estrutura-esperada))))

  (t/testing "adapter dos dados dos cartões"
    (let [caminho-arquivo    "data/in/cartoes.csv"
          fn-model           a/criar-cartao
          estrutura-esperada [{:numero   1234123412341234, :cvv  111, :validade nil, :limite   1000M, :cliente  "000.111.222-33"}
                              {:numero   4321432143214321, :cvv  222, :validade nil, :limite   2000M, :cliente  "333.444.555-66"}
                              {:numero   1598159815981598, :cvv  333, :validade nil, :limite   3000M, :cliente  "666.777.888-99"}
                              {:numero   6655665566556655, :cvv  444, :validade nil, :limite   4000M, :cliente  "666.777.888-99"}
                              {:numero   3939393939393939, :cvv  555, :validade nil, :limite   5000M, :cliente  "999.123.456-78"}]
          resultado           (a/dado-bruto->model caminho-arquivo fn-model)
          resultado-sem-obj   (map #(assoc % :validade nil) resultado)]
      (t/is (= resultado-sem-obj estrutura-esperada))))

  (t/testing "adapter dos dados das compras"
    (let [caminho-arquivo      "data/in/compras.csv"
          fn-model             a/criar-compra
          estrutura-esperada [{:data nil, :valor 129.90M, :estabelecimento "Outback", :categoria "Alimentação", :cartao 1234123412341234}
                              {:data nil, :valor 260.00M, :estabelecimento "Dentista", :categoria "Saúde", :cartao 1234123412341234}
                              {:data nil, :valor 20.00M, :estabelecimento "Cinema", :categoria "Lazer", :cartao 1234123412341234}
                              {:data nil, :valor 150.00M, :estabelecimento "Show", :categoria "Lazer", :cartao 4321432143214321}
                              {:data nil, :valor 289.99M, :estabelecimento "Posto de gasolina", :categoria "Automóvel", :cartao 4321432143214321}
                              {:data nil, :valor 79.90M, :estabelecimento "iFood", :categoria "Alimentação", :cartao 4321432143214321}
                              {:data nil, :valor 85.00M, :estabelecimento "Alura", :categoria "Educação", :cartao 4321432143214321}
                              {:data nil, :valor 85.00M, :estabelecimento "Alura", :categoria "Educação", :cartao 1598159815981598}
                              {:data nil, :valor 350.00M, :estabelecimento "Tok&Stok", :categoria "Casa", :cartao 1598159815981598}
                              {:data nil, :valor 400.00M, :estabelecimento "Leroy Merlin", :categoria "Casa", :cartao 1598159815981598}
                              {:data nil, :valor 50.00M, :estabelecimento "Madero", :categoria "Alimentação", :cartao 6655665566556655}
                              {:data nil, :valor 70.00M, :estabelecimento "Teatro", :categoria "Lazer", :cartao 6655665566556655}
                              {:data nil, :valor 250.00M, :estabelecimento "Hospital", :categoria "Saúde", :cartao 6655665566556655}
                              {:data nil, :valor 130.00M, :estabelecimento "Drogaria", :categoria "Saúde", :cartao 6655665566556655}
                              {:data nil, :valor 100.00M, :estabelecimento "Show de pagode", :categoria "Lazer", :cartao 3939393939393939}
                              {:data nil, :valor 25.90M, :estabelecimento "Dogão", :categoria "Alimentação", :cartao 3939393939393939}
                              {:data nil, :valor 215.87M, :estabelecimento "Praia", :categoria "Lazer", :cartao 3939393939393939}
                              {:data nil, :valor 976.88M, :estabelecimento "Oficina", :categoria "Automóvel", :cartao 3939393939393939}
                              {:data nil, :valor 85.00M, :estabelecimento "Alura", :categoria "Educação", :cartao 3939393939393939}]
          resultado           (a/dado-bruto->model caminho-arquivo fn-model)
          resultado-sem-obj   (map #(assoc % :data nil) resultado)]
      (t/is (= resultado-sem-obj estrutura-esperada)))))


