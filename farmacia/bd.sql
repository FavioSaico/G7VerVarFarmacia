-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-05-2022 a las 20:48:37
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `farmacia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `brands`
--

CREATE TABLE `brands` (
  `brand_id` int(11) NOT NULL,
  `brand_name` varchar(255) NOT NULL,
  `brand_active` int(11) NOT NULL DEFAULT 0,
  `brand_status` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `brands`
--

INSERT INTO `brands` (`brand_id`, `brand_name`, `brand_active`, `brand_status`) VALUES
(1, 'Cipla', 1, 1),
(2, 'Mankind', 1, 1),
(3, 'Sunpharma', 1, 1),
(4, 'MicroLabs', 1, 1),
(5, 'Pfizer', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categories`
--

CREATE TABLE `categories` (
  `categories_id` int(11) NOT NULL,
  `categories_name` varchar(255) NOT NULL,
  `categories_active` int(11) NOT NULL DEFAULT 0,
  `categories_status` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categories`
--

INSERT INTO `categories` (`categories_id`, `categories_name`, `categories_active`, `categories_status`) VALUES
(1, 'Pastillas', 1, 1),
(2, 'Jarabe', 1, 1),
(3, 'Inyecciones', 1, 1),
(4, 'Paliativos', 1, 1),
(5, 'Vacunas', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orders`
--

CREATE TABLE `orders` (
  `id` int(15) NOT NULL,
  `uno` varchar(50) NOT NULL,
  `orderDate` date NOT NULL,
  `clientName` text NOT NULL,
  `projectName` varchar(30) NOT NULL,
  `clientContact` int(15) NOT NULL,
  `address` varchar(30) NOT NULL,
  `subTotal` int(100) NOT NULL,
  `totalAmount` int(100) NOT NULL,
  `discount` int(100) NOT NULL,
  `grandTotalValue` int(100) NOT NULL,
  `gstn` int(100) NOT NULL,
  `paid` int(100) NOT NULL,
  `dueValue` int(100) NOT NULL,
  `paymentType` int(15) NOT NULL,
  `paymentStatus` int(15) NOT NULL,
  `paymentPlace` int(5) NOT NULL,
  `delete_status` tinyint(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `orders`
--

INSERT INTO `orders` (`id`, `uno`, `orderDate`, `clientName`, `projectName`, `clientContact`, `address`, `subTotal`, `totalAmount`, `discount`, `grandTotalValue`, `gstn`, `paid`, `dueValue`, `paymentType`, `paymentStatus`, `paymentPlace`, `delete_status`) VALUES
(1, 'INV-0001', '2022-02-28', 'Lucho Florez', '', 2147483647, '', 100, 10, 108, 49, 0, 49, 49, 2, 1, 0, 0),
(2, 'INV-0002', '2022-03-24', 'Bernardo Galán', '', 2147483647, '', 300, 354, 354, 354, 18, 354, 354, 1, 1, 1, 0),
(3, 'INV-0003', '2022-04-15', 'Fredy Patricio', '', 2147483647, '', 860, 1015, 10, 1005, 155, 500, 505, 2, 2, 1, 0),
(4, 'INV-0004', '2022-04-15', 'Pedro Cliente', '', 2147483647, '', 60, 71, 0, 71, 11, 50, 21, 5, 2, 1, 0),
(5, 'INV-0005', '2022-05-01', 'Juan Cliente', '', 2147483647, '', 200, 236, 0, 236, 36, 300, -64, 2, 1, 1, 0),
(6, 'INV-0006', '2022-05-01', 'Julio Cliente', '', 2147483647, '', 250, 295, 0, 295, 45, 300, -5, 2, 1, 2, 0),
(7, 'INV-0007', '2022-05-03', 'Pedro García', '', 2147483647, '', 250, 295, 0, 295, 45, 300, -5, 2, 1, 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `order_item`
--

CREATE TABLE `order_item` (
  `id` int(15) NOT NULL,
  `productName` int(100) NOT NULL,
  `quantity` varchar(255) NOT NULL,
  `rate` varchar(255) NOT NULL,
  `total` varchar(255) NOT NULL,
  `lastid` int(50) NOT NULL,
  `added_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `order_item`
--

INSERT INTO `order_item` (`id`, `productName`, `quantity`, `rate`, `total`, `lastid`, `added_date`) VALUES
(5, 2, '1', '100', '100.00', 1, '0000-00-00'),
(7, 1, '2', '30', '60.00', 3, '2022-04-15'),
(8, 2, '4', '150', '600.00', 3, '2022-04-15'),
(9, 3, '1', '200', '200.00', 3, '2022-04-15'),
(10, 1, '2', '30', '60.00', 4, '2022-04-15'),
(13, 2, '2', '150', '300.00', 2, '2022-04-15'),
(14, 3, '1', '200', '200.00', 5, '2022-05-01'),
(15, 5, '1', '250', '250.00', 6, '2022-05-01'),
(16, 5, '1', '250', '250.00', 7, '2022-05-03');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_image` text NOT NULL,
  `brand_id` int(11) NOT NULL,
  `categories_id` int(11) NOT NULL,
  `quantity` varchar(255) NOT NULL,
  `rate` varchar(255) NOT NULL,
  `mrp` int(100) NOT NULL,
  `bno` varchar(50) NOT NULL,
  `expdate` date NOT NULL,
  `added_date` date NOT NULL,
  `active` int(11) NOT NULL DEFAULT 0,
  `status` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `product_image`, `brand_id`, `categories_id`, `quantity`, `rate`, `mrp`, `bno`, `expdate`, `added_date`, `active`, `status`) VALUES
(1, 'Acetaminofen 500', 'tab.jpg', 1, 1, '50', '30', 40, '307002', '2045-02-28', '2022-02-28', 1, 1),
(2, 'Fronta 23', 'tab1.jpg', 2, 1, '30', '150', 200, '307003', '2022-02-16', '2022-02-28', 1, 1),
(3, 'Rapazol 120', 'tab3.jpg', 3, 3, '70', '200', 300, '307004', '2024-03-13', '2022-02-28', 1, 1),
(4, 'Escripvo 450', 'tab4.jpg', 4, 1, '500', '25', 30, '307005', '2050-05-31', '2022-04-15', 1, 1),
(5, 'Vacuna 123', 'vacuna pfizer.webp', 5, 5, '2500', '250', 254, '171712', '2031-06-18', '2022-05-01', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `email`) VALUES
(1, 'configuroweb', '4b67deeb9aba04a5b54632ad19934f26', 'hola@configuroweb.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`brand_id`);

--
-- Indices de la tabla `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`categories_id`);

--
-- Indices de la tabla `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `order_item`
--
ALTER TABLE `order_item`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `brands`
--
ALTER TABLE `brands`
  MODIFY `brand_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `categories`
--
ALTER TABLE `categories`
  MODIFY `categories_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `order_item`
--
ALTER TABLE `order_item`
  MODIFY `id` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

ALTER TABLE farmacia.product
ADD FOREIGN KEY (brand_id) REFERENCES brands(brand_id);


ALTER TABLE farmacia.product
ADD FOREIGN KEY (categories_id) REFERENCES categories(categories_id);



-- ALTER TABLE farmacia.order_item
-- ALTER COLUMN lastid INT(15);
ALTER TABLE farmacia.order_item
ADD FOREIGN KEY (productName) REFERENCES product(product_id);


ALTER TABLE farmacia.order_item
ADD FOREIGN KEY (lastid) REFERENCES orders(id);

