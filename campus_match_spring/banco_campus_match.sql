drop database if exists campusmatch;
create database if not exists campusmatch;
use campusmatch;

create table if not exists estudante(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    senha varchar(100) not null,
    cep varchar(9) not null,
    email varchar(100) not null,
    data_nascimento date not null,
    uuid varchar(255) not null,
    validado int not null,
    constraint pk_usuario primary key (id),
    constraint uc_estudante UNIQUE (email),
    constraint uc_estudante_uuid UNIQUE (uuid)
);

create table if not exists instituicao(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    cep varchar(9) not null,
    email varchar(100) not null,
    senha varchar(100) not null,
    numero_mec int not null,
    campus varchar(100) not null,
    publica boolean,
    sigla varchar(20),
    uuid varchar(255) not null,
    validado int not null,
    tipo varchar(10) not null,
    constraint pk_instituicao primary key (id),
    constraint uc_instituicao UNIQUE (nome, campus)
);

create table if not exists curso(
	id bigint not null auto_increment,
    nome varchar(100) not null,
    periodo enum(
		'MATUTINO',
		'VESPERTINO',
		'NOTURNO',
		'MATUTINO_VESPERTINO',
		'VESPERTINO_NOTURNO',
		'ASSINCRONO'
	),
    duracao int not null,
    modalidade enum(
		'PRESENCIAL',
        'ENSINO_A_DISTANCIA',
        'MISTO'
    ),
    area_conhecimento enum (
		'CIENCIAS_EXATAS_E_DA_TERRA',
		'CIENCIAS_BIOLOGICAS',
		'ENGENHARIAS',
		'CIENCIAS_DA_SAUDE',
		'CIENCIAS_AGRARIAS',
		'CIENCIAS_SOCIAIS_APLICADAS',
		'CIENCIAS_HUMANAS',
		'LINGUISTICA_LETRAS_E_ARTES'),
    sigla varchar(20),
    constraint pk_curso primary key (id)
);


create table if not exists infraestrutura(
	id bigint not null auto_increment,
    descricao varchar(100) not null,
    constraint pk_infraestrutura primary key (id)
);

create table if not exists publicacao(
	id bigint not null auto_increment,
    descricao varchar(100) not null,
    data_publicacao timestamp not null,
    id_instituicao bigint not null,
    constraint pk_publicacao primary key (id)
);
alter table publicacao add foreign key (id_instituicao) references instituicao(id);


create table if not exists favorito(
	id bigint not null auto_increment,
	id_estudante bigint,
    id_instituicao bigint,
    constraint pk_id_usu_uni primary key (id)
);
alter table favorito add foreign key (id_estudante) references estudante(id);
alter table favorito add foreign key (id_instituicao) references instituicao(id);

create table if not exists avaliacao(
	id bigint not null auto_increment,
    nota bigint,
    comentario varchar(200),
    data_avaliacao timestamp not null,
    id_instituicao bigint,
    id_estudante bigint,
    constraint pk_id_avl primary key (id)
);
alter table avaliacao add foreign key (id_instituicao) references instituicao(id);
alter table avaliacao add foreign key (id_estudante) references estudante(id);

create table if not exists instituicao_infraestrutura(
	id bigint not null auto_increment,
	id_instituicao bigint,
    id_infraestrutura bigint,
    constraint pk_id_inst_publ_dtp primary key (id)
);
alter table instituicao_infraestrutura add foreign key (id_instituicao) references instituicao(id);
alter table instituicao_infraestrutura add foreign key (id_infraestrutura) references infraestrutura(id);

create table if not exists instituicao_curso(
	id bigint not null auto_increment,
	id_instituicao bigint,
    id_curso bigint,
    constraint pk_id_inst_curso primary key (id)
);
alter table instituicao_curso add foreign key (id_instituicao) references instituicao(id);
alter table instituicao_curso add foreign key (id_curso) references curso(id);

create table if not exists instituicao_publicacao(
	id bigint not null auto_increment,
	id_instituicao bigint,
    id_publicacao bigint,
    constraint pk_id_inst_publ_dtp primary key (id)
);
alter table instituicao_publicacao add foreign key (id_instituicao) references instituicao(id);
alter table instituicao_publicacao add foreign key (id_publicacao) references publicacao(id);