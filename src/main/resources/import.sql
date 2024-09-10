insert into livro (id, nome, autor, editora, genero_literario, edicao, codigo_localizacao, livro_alugado) values (1, 'Maus', 'Art Spielgman', 'Quadrinhos NA CIA', 'História em quadrinhos', '12° edicao', 'A12b', true);
insert into livro (id, nome, autor, editora, genero_literario, edicao, codigo_localizacao, livro_alugado) values (2, 'É assim que acaba', 'Colleen Hoover', 'Ancora', 'Romance', '1° edição', 'B23D', false);
insert into livro (id, nome, autor, editora, genero_literario, edicao, codigo_localizacao, livro_alugado) values (3, 'É assim que acaba', 'Colleen Hoover', 'Ancora', 'Romance', '1° edição', 'B24DD', true);
insert into livro (id, nome, autor, editora, genero_literario, edicao, codigo_localizacao, livro_alugado) values (4, 'É assim que acaba', 'Colleen Hoover', 'Ancora', 'Romance', '1° edição', 'B223D', false);
insert into livro (id, nome, autor, editora, genero_literario, edicao, codigo_localizacao, livro_alugado) values (5, 'Todas as suas imperfeições', 'Colleen Hoover', 'Ancora', 'Romance', '5° edição', 'C23D', true);

insert into cliente (id, nome, cpf, data_nascimento, celular, email, endereco, menor_idade) values (1, 'Alberto Abreu', '902.358.270-53', '10/05/2001', '3341-0032', 'alberto@gamil.com', 'Rua qualquer, 10' , false);
insert into cliente (id, nome, cpf, data_nascimento, celular, email, endereco,  menor_idade) values (2, 'Bernano Bento', '298.263.530-51', '29/12/2010', '3341-2045', 'bernado@gamil.com', 'Rua alguma, 154' , false);

insert into livro_alugado(id, livro_id, cliente_id, data_locacao, data_devolucao, codigo_fiscal) values (1, 1, 2, utc_timestamp, utc_timestamp, '1234');
insert into livro_alugado(id, livro_id, cliente_id, data_locacao, data_devolucao, codigo_fiscal) values (2, 5, 1, utc_timestamp, utc_timestamp, '1234D');

insert into funcionario(id, nome, cpf,data_nascimento, email, senha, celular, endereco) values (1, "Maria Nayra", "123456789-10", utc_timestamp, "marianayra@gmail.com", "1234", "33410032", "rua alguma");
insert into funcionario(id, nome, cpf,data_nascimento, email, senha, celular, endereco) values (2, "Maria Leticia", "987654321-10", utc_timestamp, "marialeticia@gmail.com", "4321", "33410032", "rua alguma");

insert into usuarios(id, email, password) values (1, 'admin', 'ksdfjsdfh485y34ufrnwe');