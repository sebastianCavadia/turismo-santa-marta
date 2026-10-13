-- Seed coherente con mockups 01/02 (precios y cupos visibles en PNG)
INSERT INTO identitys.usuario (nombre, correo, password_hash, rol, tipo_cuenta, validado) VALUES
 ('Laura Turista','laura@demo.co','$2b$12$NXG3xv6fBBUfjdvPRKxtG.jKbuQYNMWBywzdkB8JjEiILtIDL45wO','TUR','TURISTA',TRUE),
 ('Carlos Prestador','carlos@demo.co','$2b$12$NXG3xv6fBBUfjdvPRKxtG.jKbuQYNMWBywzdkB8JjEiILtIDL45wO','PRE','HOTEL',TRUE),
 ('Ana Admin','ana@demo.co','$2b$12$8GjGLTi8/lcvOIq83UCy9uexyRAKQl3Et8B4kVa1Q0zB2EP1Rudoi','ADM','TURISTA',TRUE)
ON CONFLICT (correo) DO NOTHING;

INSERT INTO catalogo.servicio (id, nombre, descripcion, categoria, zona, precio_cop, tipo, habilitado, prestador_id) VALUES
 (1,'Playa Cristal','Playa en Parque Tayrona','Playa','Tayrona',120000,'Actividad',TRUE,2),
 (2,'Tour a Minca','Naturaleza y aventura en Minca','Naturaleza','Minca',80000,'Actividad',TRUE,2),
 (3,'City Tour Santa Marta','Cultura e historia','Cultura','Centro',50000,'Actividad',TRUE,2),
 (4,'Buceo en Taganga','Aventura y deportes','Aventura','Taganga',150000,'Actividad',TRUE,2),
 (5,'Cabaña en la Sierra','Alojamiento en naturaleza','Naturaleza','Minca',200000,'Alojamiento',TRUE,2),
 (6,'Hotel Tayrona','Habitacion estandar, Km 34 via Tayrona','Naturaleza','Tayrona',350000,'Alojamiento',TRUE,2)
ON CONFLICT (id) DO NOTHING;
SELECT setval('catalogo.servicio_id_seq', 6);

INSERT INTO catalogo.zona_capacidad (zona, capacidad) VALUES
 ('Tayrona',100),('Minca',60),('Centro',200),('Taganga',80)
ON CONFLICT DO NOTHING;

-- Disponibilidad demo: 30 días, cupos como en mockups
INSERT INTO catalogo.disponibilidad (servicio_id, fecha, cupo_total, cupo_ocupado)
SELECT s.id, d::date, CASE s.id WHEN 6 THEN 5 WHEN 1 THEN 15 WHEN 2 THEN 12 WHEN 3 THEN 20 WHEN 4 THEN 8 ELSE 6 END, 0
FROM catalogo.servicio s CROSS JOIN generate_series(CURRENT_DATE, CURRENT_DATE + 30, '1 day') d
ON CONFLICT DO NOTHING;
