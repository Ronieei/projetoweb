-- 10 pessoas medicas
INSERT INTO Pessoa (nome) VALUES ('Dr. Marcos Azevedo');
INSERT INTO Pessoa (nome) VALUES ('Dra. Fernanda Borges');
INSERT INTO Pessoa (nome) VALUES ('Dr. Ricardo Lima');
INSERT INTO Pessoa (nome) VALUES ('Dra. Juliana Costa');
INSERT INTO Pessoa (nome) VALUES ('Dr. Eduardo Silva');
INSERT INTO Pessoa (nome) VALUES ('Dra. Patricia Santos');
INSERT INTO Pessoa (nome) VALUES ('Dr. Gustavo Mendes');
INSERT INTO Pessoa (nome) VALUES ('Dra. Laura Ferreira');
INSERT INTO Pessoa (nome) VALUES ('Dr. André Oliveira');
INSERT INTO Pessoa (nome) VALUES ('Dra. Sofia Almeida');

-- 10 medicos
INSERT INTO medico (id_pessoa, crm) VALUES (1, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (2, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (3, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (4, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (5, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (6, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (7, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (8, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (9, 'CRM12345');
INSERT INTO medico (id_pessoa, crm) VALUES (10, 'CRM12345');

-- 10 pacientes
INSERT INTO pessoa (nome) VALUES ('Ana Silva');
INSERT INTO pessoa (nome) VALUES ('Bruno Santos');
INSERT INTO pessoa (nome) VALUES ('Carla Oliveira');
INSERT INTO pessoa (nome) VALUES ('Daniel Costa');
INSERT INTO pessoa (nome) VALUES ('Elisa Ferreira');
INSERT INTO pessoa (nome) VALUES ('Fábio Almeida');
INSERT INTO pessoa (nome) VALUES ('Gabriela Lima');
INSERT INTO pessoa (nome) VALUES ('Hugo Pereira');
INSERT INTO pessoa (nome) VALUES ('Isabela Rocha');
INSERT INTO pessoa (nome) VALUES ('João Mendes');


INSERT INTO paciente (id_pessoa, telefone) VALUES (11, '11987654321');
INSERT INTO paciente (id_pessoa, telefone) VALUES (12, '11987654322');
INSERT INTO paciente (id_pessoa, telefone) VALUES (13, '11987654323');
INSERT INTO paciente (id_pessoa, telefone) VALUES (14, '11987654324');
INSERT INTO paciente (id_pessoa, telefone) VALUES (15, '11987654325');
INSERT INTO paciente (id_pessoa, telefone) VALUES (16, '11987654326');
INSERT INTO paciente (id_pessoa, telefone) VALUES (17, '11987654327');
INSERT INTO paciente (id_pessoa, telefone) VALUES (18, '11987654328');
INSERT INTO paciente (id_pessoa, telefone) VALUES (19, '11987654329');
INSERT INTO paciente (id_pessoa, telefone) VALUES (20, '11987654330');



-- Inserindo Disponibilidades para o médico
-- INSERT INTO Disponibilidade (medico_id_pessoa, data_inicial, data_final, horario_inicio, horario_fim, intervalo_inicio, intervalo_fim, tempo_consulta) VALUES (1, '2024-07-17', '2024-07-18', '07:00:00', '14:00:00', '12:00:00', '13:00:00', '00:15:00'); -- disponivel pela manha e parcialmente a tarde e com intervalo de 1 hora , tempo para cada consulta 15
-- INSERT INTO Disponibilidade (medico_id_pessoa, data_inicial, data_final, horario_inicio, horario_fim, intervalo_inicio, intervalo_fim, tempo_consulta) VALUES (1, '2024-07-19', '2024-07-19', '07:00:00', '12:00:00', '00:00:00', '00:00:00', '00:40:00'); -- disponivel pela manha e sem intervalo , tempo para cada consulta 40


-- INSERT INTO Disponibilidade (medico_id_pessoa, data_inicial, data_final, horario_inicio, horario_fim, intervalo_inicio, intervalo_fim, tempo_consulta) VALUES (1,'2025-07-10', '2025-07-15', '13:00:00', '18:00:00', '00:00:00', '00:00:00', '00:30:00'); -- disponivel de tarde e sem intervalo , tempo para cada consulta 30
-- INSERT INTO Disponibilidade (medico_id_pessoa, data_inicial, data_final, horario_inicio, horario_fim, intervalo_inicio, intervalo_fim, tempo_consulta) VALUES (1, '2025-07-16', '2025-07-16', '07:00:00', '18:00:00', '12:00:00', '14:00:00', '00:15:00'); -- disponivel integralmente e  com intervalo de 2 horas, , tempo para cada consulta 15
-- INSERT INTO Disponibilidade (medico_id_pessoa, data_inicial, data_final, horario_inicio, horario_fim, intervalo_inicio, intervalo_fim, tempo_consulta) VALUES (1, '2025-07-17', '2025-07-18', '07:00:00', '14:00:00', '12:00:00', '13:00:00', '00:15:00'); -- disponivel pela manha e parcialmente a tarde e com intervalo de 1 hora , tempo para cada consulta 15
-- INSERT INTO Disponibilidade (medico_id_pessoa, data_inicial, data_final, horario_inicio, horario_fim, intervalo_inicio, intervalo_fim, tempo_consulta) VALUES (1,'2025-07-19', '2025-07-19', '07:00:00', '12:00:00', '00:00:00', '00:00:00', '00:40:00'); -- disponivel pela manha e sem intervalo , tempo para cada consulta 40

-- PAPEIS E FUNÇÕES

INSERT INTO role (nome) VALUES ('ROLE_ADMIN');
INSERT INTO role (nome) VALUES ('ROLE_PACIENTE');
INSERT INTO role (nome) VALUES ('ROLE_MEDICO');
INSERT INTO role (nome) VALUES ('ROLE_SECRETARIA');

-- admin
INSERT INTO usuario(login, password) VALUES ('admin', '$2a$10$eHgZUBCzap5m0trPXpA0/e.0MxIkMDc./eswc8JPx1SZvnzO4hA4C'); -- senha 123
-- medico1
INSERT INTO usuario(login, password) VALUES ('medico1', '$2a$10$BbRbTfdIk1QAt4VX8/7tb.VlKvFYzx60z6D6mHFP5oVNABoSvq8Oe'); -- senha 123
-- secretaria
INSERT INTO usuario(login, password) VALUES ('secretaria1', '$2a$10$W55SaWi0Vhn55uy/6pQe6ONT8Ijvq7enYOsDR5C90NNgrMF9cht1a'); -- senha 123

INSERT INTO usuario_roles(roles_id, usuarios_id) VALUES (1,1)

-- ESTADO

INSERT INTO estado (nome, sigla) VALUES ('Acre', 'AC');
INSERT INTO estado (nome, sigla) VALUES ('Alagoas', 'AL');
INSERT INTO estado (nome, sigla) VALUES ('Amapá', 'AP');
INSERT INTO estado (nome, sigla) VALUES ('Amazonas', 'AM');
INSERT INTO estado (nome, sigla) VALUES ('Bahia', 'BA');
INSERT INTO estado (nome, sigla) VALUES ('Ceará', 'CE');
INSERT INTO estado (nome, sigla) VALUES ('Distrito Federal', 'DF');
INSERT INTO estado (nome, sigla) VALUES ('Espírito Santo', 'ES');
INSERT INTO estado (nome, sigla) VALUES ('Goiás', 'GO');
INSERT INTO estado (nome, sigla) VALUES ('Maranhão', 'MA');
INSERT INTO estado (nome, sigla) VALUES ('Mato Grosso', 'MT');
INSERT INTO estado (nome, sigla) VALUES ('Mato Grosso do Sul', 'MS');
INSERT INTO estado (nome, sigla) VALUES ('Minas Gerais', 'MG');
INSERT INTO estado (nome, sigla) VALUES ('Pará', 'PA');
INSERT INTO estado (nome, sigla) VALUES ('Paraíba', 'PB');
INSERT INTO estado (nome, sigla) VALUES ('Paraná', 'PR');
INSERT INTO estado (nome, sigla) VALUES ('Pernambuco', 'PE');
INSERT INTO estado (nome, sigla) VALUES ('Piauí', 'PI');
INSERT INTO estado (nome, sigla) VALUES ('Rio de Janeiro', 'RJ');
INSERT INTO estado (nome, sigla) VALUES ('Rio Grande do Norte', 'RN');
INSERT INTO estado (nome, sigla) VALUES ('Rio Grande do Sul', 'RS');
INSERT INTO estado (nome, sigla) VALUES ('Rondônia', 'RO');
INSERT INTO estado (nome, sigla) VALUES ('Roraima', 'RR');
INSERT INTO estado (nome, sigla) VALUES ('Santa Catarina', 'SC');
INSERT INTO estado (nome, sigla) VALUES ('São Paulo', 'SP');
INSERT INTO estado (nome, sigla) VALUES ('Sergipe', 'SE');
INSERT INTO estado (nome, sigla) VALUES ('Tocantins', 'TO');

-- CIDADES

-- Acre (id = 1)
INSERT INTO cidade (nome, estado_id) VALUES ('Rio Branco', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Cruzeiro do Sul', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Sena Madureira', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Tarauacá', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Feijó', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Brasileia', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Plácido de Castro', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Xapuri', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Mâncio Lima', 1);
INSERT INTO cidade (nome, estado_id) VALUES ('Porto Acre', 1);

-- Alagoas (id = 2)
INSERT INTO cidade (nome, estado_id) VALUES ('Maceió', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Arapiraca', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Palmeira dos Índios', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Rio Largo', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Penedo', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('União dos Palmares', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Coruripe', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('São Miguel dos Campos', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Delmiro Gouveia', 2);
INSERT INTO cidade (nome, estado_id) VALUES ('Marechal Deodoro', 2);

-- Amapá (id = 3)
INSERT INTO cidade (nome, estado_id) VALUES ('Macapá', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Santana', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Laranjal do Jari', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Oiapoque', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Mazagão', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Porto Grande', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Pedra Branca do Amapari', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Tartarugalzinho', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Vitória do Jari', 3);
INSERT INTO cidade (nome, estado_id) VALUES ('Calçoene', 3);

-- Amazonas (id = 4)
INSERT INTO cidade (nome, estado_id) VALUES ('Manaus', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Parintins', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Itacoatiara', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Manacapuru', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Coari', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Tefé', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Tabatinga', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Maués', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Benjamin Constant', 4);
INSERT INTO cidade (nome, estado_id) VALUES ('Iranduba', 4);

-- Bahia (id = 5)
INSERT INTO cidade (nome, estado_id) VALUES ('Salvador', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Feira de Santana', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Vitória da Conquista', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Camaçari', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Itabuna', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Juazeiro', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Lauro de Freitas', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Ilhéus', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Jequié', 5);
INSERT INTO cidade (nome, estado_id) VALUES ('Alagoinhas', 5);

-- Ceará (id = 6)
INSERT INTO cidade (nome, estado_id) VALUES ('Fortaleza', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Caucaia', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Juazeiro do Norte', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Maracanaú', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Sobral', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Crato', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Itapipoca', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Maranguape', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Iguatu', 6);
INSERT INTO cidade (nome, estado_id) VALUES ('Quixadá', 6);

-- Distrito Federal (id = 7)
INSERT INTO cidade (nome, estado_id) VALUES ('Brasília', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Ceilândia', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Taguatinga', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Samambaia', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Planaltina', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Sobradinho', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Recanto das Emas', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Gama', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Guará', 7);
INSERT INTO cidade (nome, estado_id) VALUES ('Santa Maria', 7);

-- Espírito Santo (id = 8)
INSERT INTO cidade (nome, estado_id) VALUES ('Vitória', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('Vila Velha', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('Serra', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('Cariacica', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('Linhares', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('Cachoeiro de Itapemirim', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('Guarapari', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('Aracruz', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('Colatina', 8);
INSERT INTO cidade (nome, estado_id) VALUES ('São Mateus', 8);

-- Goiás (id = 9)
INSERT INTO cidade (nome, estado_id) VALUES ('Goiânia', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Aparecida de Goiânia', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Anápolis', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Rio Verde', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Luziânia', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Águas Lindas de Goiás', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Formosa', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Trindade', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Senador Canedo', 9);
INSERT INTO cidade (nome, estado_id) VALUES ('Catalão', 9);


-- Maranhão (id = 10)
INSERT INTO cidade (nome, estado_id) VALUES ('São Luís', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('Imperatriz', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('São José de Ribamar', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('Timon', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('Caxias', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('Codó', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('Paço do Lumiar', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('Açailândia', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('Bacabal', 10);
INSERT INTO cidade (nome, estado_id) VALUES ('Balsas', 10);

-- Mato Grosso (id = 11)
INSERT INTO cidade (nome, estado_id) VALUES ('Cuiabá', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Várzea Grande', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Rondonópolis', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Sinop', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Tangará da Serra', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Cáceres', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Sorriso', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Lucas do Rio Verde', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Primavera do Leste', 11);
INSERT INTO cidade (nome, estado_id) VALUES ('Barra do Garças', 11);

-- Mato Grosso do Sul (id = 12)
INSERT INTO cidade (nome, estado_id) VALUES ('Campo Grande', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Dourados', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Três Lagoas', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Corumbá', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Ponta Porã', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Naviraí', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Nova Andradina', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Sidrolândia', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Aquidauana', 12);
INSERT INTO cidade (nome, estado_id) VALUES ('Maracaju', 12);

-- Minas Gerais (id = 13)
INSERT INTO cidade (nome, estado_id) VALUES ('Belo Horizonte', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Uberlândia', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Contagem', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Juiz de Fora', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Betim', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Montes Claros', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Uberaba', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Governador Valadares', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Ipatinga', 13);
INSERT INTO cidade (nome, estado_id) VALUES ('Sete Lagoas', 13);

-- Pará (id = 14)
INSERT INTO cidade (nome, estado_id) VALUES ('Belém', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Ananindeua', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Santarém', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Marabá', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Castanhal', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Parauapebas', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Tucuruí', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Bragança', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Abaetetuba', 14);
INSERT INTO cidade (nome, estado_id) VALUES ('Cametá', 14);

-- Paraíba (id = 15)
INSERT INTO cidade (nome, estado_id) VALUES ('João Pessoa', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Campina Grande', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Santa Rita', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Patos', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Bayeux', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Sousa', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Cabedelo', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Cajazeiras', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Guarabira', 15);
INSERT INTO cidade (nome, estado_id) VALUES ('Itabaiana', 15);

-- Paraná (id = 16)
INSERT INTO cidade (nome, estado_id) VALUES ('Curitiba', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('Londrina', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('Maringá', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('Ponta Grossa', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('Cascavel', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('São José dos Pinhais', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('Foz do Iguaçu', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('Colombo', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('Guarapuava', 16);
INSERT INTO cidade (nome, estado_id) VALUES ('Paranaguá', 16);

-- Pernambuco (id = 17)
INSERT INTO cidade (nome, estado_id) VALUES ('Recife', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Jaboatão dos Guararapes', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Olinda', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Caruaru', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Petrolina', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Paulista', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Cabo de Santo Agostinho', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Garanhuns', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Vitória de Santo Antão', 17);
INSERT INTO cidade (nome, estado_id) VALUES ('Igarassu', 17);

-- Piauí (id = 18)
INSERT INTO cidade (nome, estado_id) VALUES ('Teresina', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('Parnaíba', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('Picos', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('Floriano', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('Piripiri', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('Campo Maior', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('Barras', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('José de Freitas', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('Altos', 18);
INSERT INTO cidade (nome, estado_id) VALUES ('Esperantina', 18);

-- Rio de Janeiro (id = 19)
INSERT INTO cidade (nome, estado_id) VALUES ('Rio de Janeiro', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('São Gonçalo', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('Duque de Caxias', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('Nova Iguaçu', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('Niterói', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('Belford Roxo', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('Campos dos Goytacazes', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('Petrópolis', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('Volta Redonda', 19);
INSERT INTO cidade (nome, estado_id) VALUES ('Macaé', 19);

-- Rio Grande do Norte (id = 20)
INSERT INTO cidade (nome, estado_id) VALUES ('Natal', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('Mossoró', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('Parnamirim', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('Caicó', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('Açu', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('Currais Novos', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('São Gonçalo do Amarante', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('Macau', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('Ceará-Mirim', 20);
INSERT INTO cidade (nome, estado_id) VALUES ('Pau dos Ferros', 20);

-- Rio Grande do Sul (id = 21)
INSERT INTO cidade (nome, estado_id) VALUES ('Porto Alegre', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('Caxias do Sul', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('Pelotas', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('Canoas', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('Santa Maria', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('Gravataí', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('Viamão', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('Novo Hamburgo', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('São Leopoldo', 21);
INSERT INTO cidade (nome, estado_id) VALUES ('Rio Grande', 21);

-- Rondônia (id = 22)
INSERT INTO cidade (nome, estado_id) VALUES ('Porto Velho', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Ji-Paraná', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Ariquemes', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Cacoal', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Rolim de Moura', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Vilhena', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Guajará-Mirim', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Machadinho ', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Jaru', 22);
INSERT INTO cidade (nome, estado_id) VALUES ('Ouro Preto do Oeste', 22);

-- Roraima (id = 23)
INSERT INTO cidade (nome, estado_id) VALUES ('Boa Vista', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('Rorainópolis', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('Caracaraí', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('Alto Alegre', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('Mucajaí', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('Amajari', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('Pacaraima', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('Normandia', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('Iracema', 23);
INSERT INTO cidade (nome, estado_id) VALUES ('São João da Baliza', 23);

-- Santa Catarina (id = 24)
INSERT INTO cidade (nome, estado_id) VALUES ('Florianópolis', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('Joinville', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('Blumenau', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('Chapecó', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('Itajaí', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('Criciúma', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('Lages', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('Jaraguá do Sul', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('Balneário Camboriú', 24);
INSERT INTO cidade (nome, estado_id) VALUES ('São José', 24);

-- São Paulo (id = 25)
INSERT INTO cidade (nome, estado_id) VALUES ('São Paulo', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('Guarulhos', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('Campinas', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('São Bernardo do Campo', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('Santo André', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('Osasco', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('São José dos Campos', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('Ribeirão Preto', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('Sorocaba', 25);
INSERT INTO cidade (nome, estado_id) VALUES ('Santos', 25);

-- Sergipe (id = 26)
INSERT INTO cidade (nome, estado_id) VALUES ('Aracaju', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('Nossa Senhora do Socorro', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('Lagarto', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('Itabaiana', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('Estância', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('São Cristóvão', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('Tobias Barreto', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('Simão Dias', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('Propriá', 26);
INSERT INTO cidade (nome, estado_id) VALUES ('Capela', 26);

-- Tocantins (id = 27)
INSERT INTO cidade (nome, estado_id) VALUES ('Palmas', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Araguaína', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Gurupi', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Porto Nacional', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Paraíso do Tocantins', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Colinas do Tocantins', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Dianópolis', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Tocantinópolis', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Miracema do Tocantins', 27);
INSERT INTO cidade (nome, estado_id) VALUES ('Formoso do Araguaia', 27);


-- ednreco
INSERT INTO endereco (logradouro, bairro, cidade_id) VALUES ('Rua das Flores','Centro', 261);

-- pessoa PACIENTE
INSERT INTO pessoa (nome, endereco_id) VALUES ('paciente de teste', 1);
INSERT INTO paciente (id_pessoa, telefone) VALUES (21, '11987654330');