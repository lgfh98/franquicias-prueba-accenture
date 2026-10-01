INSERT INTO franchises (name) VALUES ('Franquicia Juan Valdez');
INSERT INTO franchises (name) VALUES ('Franquicia Hamburguesas El Corral');
INSERT INTO franchises (name) VALUES ('Franquicia Frisby');

INSERT INTO branches (franchise_id, name) VALUES (1, 'Sucursal Zona Rosa');
INSERT INTO branches (franchise_id, name) VALUES (1, 'Sucursal Aeropuerto');
INSERT INTO branches (franchise_id, name) VALUES (1, 'Sucursal Centro Internacional');

INSERT INTO branches (franchise_id, name) VALUES (2, 'Sucursal Chapinero');
INSERT INTO branches (franchise_id, name) VALUES (2, 'Sucursal Unicentro');

INSERT INTO branches (franchise_id, name) VALUES (3, 'Sucursal Salitre Plaza');

INSERT INTO products (branch_id, name, stock) VALUES (1, 'Café Latte', 25);
INSERT INTO products (branch_id, name, stock) VALUES (1, 'Café Americano', 80);
INSERT INTO products (branch_id, name, stock) VALUES (1, 'Pastel de Pollo', 15);

INSERT INTO products (branch_id, name, stock) VALUES (2, 'Cold Brew', 110);
INSERT INTO products (branch_id, name, stock) VALUES (2, 'Mocaccino', 30);
INSERT INTO products (branch_id, name, stock) VALUES (2, 'Capuchino Vainilla', 65);

INSERT INTO products (branch_id, name, stock) VALUES (3, 'Granizado de Café', 95);
INSERT INTO products (branch_id, name, stock) VALUES (3, 'Té Chai', 40);
INSERT INTO products (branch_id, name, stock) VALUES (3, 'Galleta de Avena', 50);

INSERT INTO products (branch_id, name, stock) VALUES (4, 'Corralísima Doble', 140);
INSERT INTO products (branch_id, name, stock) VALUES (4, 'Papas Medianas', 85);
INSERT INTO products (branch_id, name, stock) VALUES (4, 'Malteada de Chocolate', 50);

INSERT INTO products (branch_id, name, stock) VALUES (5, 'Vaquero Criollo', 70);
INSERT INTO products (branch_id, name, stock) VALUES (5, 'Aros de Cebolla', 120);
INSERT INTO products (branch_id, name, stock) VALUES (5, 'Gaseosa 16oz', 90);

INSERT INTO products (branch_id, name, stock) VALUES (6, 'Combo Frisnack', 200);
INSERT INTO products (branch_id, name, stock) VALUES (6, 'Trocipollos', 60);
