-- ----------------------------
-- Records of parametro
-- ----------------------------
INSERT INTO parametro(id, descripcion, nombre, valor, tipo) VALUES ('1', 'Máximo de cantidad permitido modificar sin advertencia', 'producto.cantidad.warning', '3', '2');
-- ----------------------------
-- Records of direccion
-- ----------------------------
INSERT INTO direccion(id, domicilio, codigopostal, ciudad_id) VALUES ('1', 'Av. Freyre 1752', '3000', '16391');

-- ----------------------------
-- Records of empresa cliente
-- ----------------------------
INSERT INTO empresa(id, nombre, fechaInicioActividad,cuit, ingBrutos, telefono, email, tipoResponsable, numeroFacturaActual, direccion_id) 
VALUES ('1', 'La Isla', '02-08-2011', '27-28764155-4', 'Responable Inscripto', '03424529927', 'demovendemas@facebook.com', 'Responable Inscripto', '1', '1');

-- ----------------------------
-- Records of tipo de productos
-- ----------------------------
INSERT INTO tipo(id, descripcion) values('1','Pijama');
INSERT INTO tipo(id, descripcion) values('2','Urbano');
INSERT INTO tipo(id, descripcion) values('3','Lenceria');
INSERT INTO tipo(id, descripcion) values('4','Accesorio');
INSERT INTO tipo(id, descripcion) values('5','Verano');
INSERT INTO tipo(id, descripcion) values('6','Vestido');

-- ----------------------------
-- Records of marca de productos
-- ----------------------------
INSERT INTO marca(id, descripcion) values('1','Mada');	 
INSERT INTO marca(id, descripcion) values('2','Sweet Victorian');	 
INSERT INTO marca(id, descripcion) values('3','Class Life');	 
INSERT INTO marca(id, descripcion) values('4','Admit One');	 
INSERT INTO marca(id, descripcion) values('5','S-Mode');	 

-- ----------------------------
-- Records of productos
-- ----------------------------
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado) 
VALUES ('0', '*', 'Comodin', '0', '0', '0', '0', '0', 'false');	

INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado, tipo_id, marca_id) 
VALUES ('1', '11', 'Bikini Vuelos Marron', '10', '5', '5', '296.70', '62.31', 'false', '5', '5');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado, tipo_id, marca_id)  
VALUES ('2', '12', 'Campera Capucha blanca',  '10', '5', '5', '230.58', '48.42', 'false', '2', '2');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado, tipo_id, marca_id) 
VALUES ('3', '13', 'Bikini Perlas',  '10', '5', '5', '40.50', '8.51', 'false', '5', '4');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado, tipo_id, marca_id) 
VALUES ('4', '14', 'Pantalon Negro deportivo',  '10', '5', '5', '95.04', '19.96', 'false', '2', '4');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado)
VALUES ('5', '15', 'Bikini Lunares',  '10', '5', '5', '114.88', '24.12', 'false');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado)
VALUES ('6', '16', 'Calza Deportiva',  '10', '5', '5', '404.13', '84.87', 'false');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado)
VALUES ('7', '17', 'Campera sin mangas Capucha',  '10', '5', '5', '189.26', '39.74', 'false');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado)
VALUES ('8', '18', 'Remera V',  '10', '5', '5', '313.22', '65.78', 'false');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado)
VALUES ('9', '19', 'Remera deportiva',  '10', '5', '5', '173.55', '36.45', 'false');
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado)
VALUES ('10', '111', 'Medias lunares', '10', '5', '5', '194.22', '40.79', 'false');
