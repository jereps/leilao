-- 1. Inserir Utilizadores na tabela 'users'
-- As passwords estão em hash BCrypt
-- senha: 'admin' -> $2a$10$ancA8a5yLD4eJKA6qNN2Ae35y6DvTCAmFUMCu7BZimCajQ8llLMl.
INSERT INTO users (nome, email, password)
VALUES ('admin', 'admin@gmail.com', '$2a$10$ancA8a5yLD4eJKA6qNN2Ae35y6DvTCAmFUMCu7BZimCajQ8llLMl.');

-- senha: 'user'  -> $2a$10$fsSKeb6deoAUun3tWVJf1.SA.MpqX.LNIQZq.ycV3wSfuhRs8S8w2
INSERT INTO users (nome, email, password)
VALUES ('user', 'user@gmail.com', '$2a$10$fsSKeb6deoAUun3tWVJf1.SA.MpqX.LNIQZq.ycV3wSfuhRs8S8w2');

-- 2. Associar Permissões na tabela 'user_roles'

INSERT INTO user_roles (user_id, roles)
SELECT id, 'ROLE_ADMIN' FROM users WHERE email = 'admin@gmail.com';

INSERT INTO user_roles (user_id, roles)
SELECT id, 'ROLE_USER' FROM users WHERE email = 'user@gmail.com';