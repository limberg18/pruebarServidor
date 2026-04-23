-- =====================================
-- DATOS DE PRUEBA PARA TALLER MECÁNICO
-- =====================================

-- ====================
-- USUARIOS
-- ====================
INSERT INTO usuarios (username, password, nombre, email, rol, activo) VALUES
('admin01', '1234', 'Administrador', 'admin@gmail.com', 'ADMIN', true),
('cliente01', '1234', 'Juan Pérez', 'juan@gmail.com', 'CLIENTE', true),
('cliente02', '1234', 'Carlos Ramírez', 'carlos@gmail.com', 'CLIENTE', true),
('cliente03', '1234', 'María López', 'maria@gmail.com', 'CLIENTE', true);

-- ====================
-- CLIENTES
-- ====================
INSERT INTO cliente (tipo_documento, nro_documento, nombre, direccion, telefono, email) VALUES
('DNI', '45871236', 'Juan Pérez', 'Av. Los Mecánicos 123 - Lima', '987654321', 'juan.perez@gmail.com'),
('DNI', '73458291', 'Carlos Ramírez', 'Jr. Los Talleres 456 - Callao', '912345678', 'carlos.ramirez@gmail.com'),
('DNI', '68921475', 'María López', 'Av. Industrial 890 - San Juan de Lurigancho', '956789123', 'maria.lopez@gmail.com');

-- ====================
-- PRODUCTOS
-- ====================
-- Aceites
INSERT INTO producto (codigo, descripcion, precio_compra, precio_venta, stock) VALUES
('TM-ACE-001', 'Aceite de motor sintético 5W30 5L', 190.00, 280.00, 35),
('TM-ACE-002', 'Aceite de motor mineral 20W50 4L', 110.00, 170.00, 40);

-- Filtros
INSERT INTO producto (codigo, descripcion, precio_compra, precio_venta, stock) VALUES
('TM-FIL-001', 'Filtro de aceite Toyota', 12.00, 28.00, 100),
('TM-FIL-002', 'Filtro de aire Hyundai', 45.00, 85.00, 60),
('TM-FIL-003', 'Filtro de combustible Nissan', 35.00, 70.00, 50);

-- Bujías y bobinas
INSERT INTO producto (codigo, descripcion, precio_compra, precio_venta, stock) VALUES
('TM-BUJ-001', 'Juego de bujías NGK (4 unidades)', 65.00, 120.00, 45),
('TM-BOB-001', 'Bobina de encendido Toyota', 180.00, 320.00, 15);

-- Frenos
INSERT INTO producto (codigo, descripcion, precio_compra, precio_venta, stock) VALUES
('TM-FRE-001', 'Pastillas de freno delanteras Toyota', 160.00, 300.00, 25),
('TM-FRE-002', 'Disco de freno delantero', 220.00, 420.00, 20);

-- Suspensión
INSERT INTO producto (codigo, descripcion, precio_compra, precio_venta, stock) VALUES
('TM-SUS-001', 'Amortiguador delantero KYB', 280.00, 520.00, 18),
('TM-SUS-002', 'Terminal de dirección', 70.00, 140.00, 40);

-- Transmisión y motor
INSERT INTO producto (codigo, descripcion, precio_compra, precio_venta, stock) VALUES
('TM-MOT-001', 'Correa de distribución', 150.00, 280.00, 22),
('TM-MOT-002', 'Bomba de agua', 200.00, 380.00, 15);