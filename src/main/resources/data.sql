INSERT INTO tb_cliente( cpf, criado_em, data_nascimento, email , nome_completo, renda_mensal , telefone, tipo ) VALUES ( '12312345678', '2021-11-08 13:07', '2000-10-10',   'pedro@gmail.com', 'Pedro Carlos', 3570.00 ,  '(88)987541236',  1);
INSERT INTO tb_cliente( cpf, criado_em, data_nascimento, email , nome_completo, renda_mensal , telefone, tipo ) VALUES ( '92312345679', '2021-11-08 13:07', '2000-10-10',   'jao@gmail.com', 'Joao Tavares', 3570.00 ,  '(88)987541236',  1);
INSERT INTO tb_cliente( cpf, criado_em, data_nascimento, email , nome_completo, renda_mensal , telefone, tipo ) VALUES ( '82312345676', '2021-11-08 13:07', '2000-10-10',   'Algusto@gmail.com', 'Algusto Carlos', 3570.00 ,  '(88)987541236',  1);
INSERT INTO tb_cliente( cpf, criado_em, data_nascimento, email , nome_completo, renda_mensal , telefone, tipo ) VALUES ( '72312345674', '2021-11-08 13:07', '2000-10-10',   'Lopes@gmail.com', 'Lopes', 3570.00 ,  '(88)987541236',  1);
INSERT INTO tb_cliente( cpf, criado_em, data_nascimento, email , nome_completo, renda_mensal , telefone, tipo ) VALUES ( '62312345671', '2021-11-08 13:07', '2000-10-10',   'pedro@gmail.com', 'Pedro Carlos', 3570.00 ,  '(88)987541236',  1);


INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1245', '2021-11-08 13:07',   2000,  3570.00 , 1,1,1);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1246', '2021-11-08 13:07',   4000,  2570.00 , 1,1,2);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1247', '2021-11-08 13:07',   5000,  3570.00 , 1,1,3);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1248', '2021-11-08 13:07',   6000,  5570.00 , 1,1,4);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1249', '2021-11-08 13:07',   8000,  8570.00 , 1,1,5);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1250', '2021-11-08 13:07',   10000,  3570.00 , 1,1,1);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1251', '2021-11-08 13:07',   3500,  8570.00 , 1,1,2);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1252', '2021-11-08 13:07',   2500,  5570.00 , 1,1,3);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1253', '2021-11-08 13:07',   4500,  5570.00 , 1,1,4);
INSERT INTO tb_conta( agencia,  conta, criado_em , limite_credito, saldo , status, tipo, cliente_id ) VALUES ( '1144', '1254', '2021-11-08 13:07',   2500,  5570.00 , 1,1,5);


INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 100, 1,1,1,2);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,2,3);


INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,3,2);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,4,3);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,5,4);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,6,5);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,7,6);

INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,8,7);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,9,8);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,8,9);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,7,1);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,6,2);

INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,5,2);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,4,3);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,3,4);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,2,5);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,1,6);

INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,3,7);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,2,8);
INSERT INTO tb_transacao(criado_em, montante, status, tipo, conta_destino_id, conta_origem_id ) VALUES ('2021-11-08 13:07', 200, 1,1,1,9);



