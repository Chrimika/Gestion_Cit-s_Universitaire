-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 22 jan. 2024 à 09:07
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestion_cites`
--

-- --------------------------------------------------------

--
-- Structure de la table `batiments`
--

CREATE TABLE `batiments` (
  `id` int(11) NOT NULL,
  `code` varchar(30) NOT NULL,
  `prix_chambres` int(11) NOT NULL,
  `localisation` varchar(50) NOT NULL,
  `nbr_chambres` int(11) NOT NULL,
  `nbr_lits_chambres` int(11) NOT NULL,
  `nbr_niv` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `batiments`
--

INSERT INTO `batiments` (`id`, `code`, `prix_chambres`, `localisation`, `nbr_chambres`, `nbr_lits_chambres`, `nbr_niv`) VALUES
(1, 'BatA', 2000, 'pres de la cantine 1', 10, 2, 3),
(2, 'BatB', 3000, 'pres de la cantine 2', 20, 2, 3);

-- --------------------------------------------------------

--
-- Structure de la table `chambres`
--

CREATE TABLE `chambres` (
  `id` int(11) NOT NULL,
  `code` varchar(20) NOT NULL,
  `id_batiment` int(11) NOT NULL,
  `niv` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `chambres`
--

INSERT INTO `chambres` (`id`, `code`, `id_batiment`, `niv`) VALUES
(2, 'A1', 1, 3),
(3, 'A2', 1, 3),
(4, 'A3', 1, 3),
(5, 'A4', 1, 3),
(6, 'A6', 1, 3),
(7, 'B1', 2, 2),
(8, 'A7', 1, 3),
(9, 'A8', 1, 3),
(10, 'A9', 1, 3),
(11, 'A10', 1, 3),
(12, 'A11', 1, 3),
(13, 'A12', 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `douches`
--

CREATE TABLE `douches` (
  `id` int(11) NOT NULL,
  `douche` tinyint(1) NOT NULL,
  `benoire` tinyint(1) NOT NULL,
  `id_chambre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `etat_lit`
--

CREATE TABLE `etat_lit` (
  `annee` int(11) NOT NULL,
  `id_occupant` int(11) NOT NULL,
  `id_lit` int(11) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `etudiants`
--

CREATE TABLE `etudiants` (
  `id` int(11) NOT NULL,
  `Mat` varchar(20) NOT NULL,
  `handicap` tinyint(1) NOT NULL,
  `sexe` text NOT NULL,
  `age` int(11) NOT NULL,
  `niveau` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `lits`
--

CREATE TABLE `lits` (
  `id` int(11) NOT NULL,
  `Num` int(11) NOT NULL,
  `id_chambre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `lits`
--

INSERT INTO `lits` (`id`, `Num`, `id_chambre`) VALUES
(2, 2, 3);

-- --------------------------------------------------------

--
-- Structure de la table `mois`
--

CREATE TABLE `mois` (
  `id` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `paie` tinyint(1) NOT NULL,
  `id_lit` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `occupant`
--

CREATE TABLE `occupant` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `date_entree` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `address` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(256) NOT NULL,
  `phone` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `address`, `email`, `name`, `password`, `phone`) VALUES
(1, 'Damas', '123', 'Mika', '123456', '672094167'),
(2, 'folon', 'xy.z', 'xyz', '123456', '125');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `batiments`
--
ALTER TABLE `batiments`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `chambres`
--
ALTER TABLE `chambres`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_batiment` (`id_batiment`);

--
-- Index pour la table `douches`
--
ALTER TABLE `douches`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_chambre` (`id_chambre`);

--
-- Index pour la table `etat_lit`
--
ALTER TABLE `etat_lit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_lit` (`id_lit`),
  ADD KEY `id_occupant` (`id_occupant`);

--
-- Index pour la table `etudiants`
--
ALTER TABLE `etudiants`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `lits`
--
ALTER TABLE `lits`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_chambre` (`id_chambre`);

--
-- Index pour la table `mois`
--
ALTER TABLE `mois`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_lit` (`id_lit`);

--
-- Index pour la table `occupant`
--
ALTER TABLE `occupant`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `batiments`
--
ALTER TABLE `batiments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `chambres`
--
ALTER TABLE `chambres`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `douches`
--
ALTER TABLE `douches`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `etat_lit`
--
ALTER TABLE `etat_lit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `etudiants`
--
ALTER TABLE `etudiants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `lits`
--
ALTER TABLE `lits`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `mois`
--
ALTER TABLE `mois`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `occupant`
--
ALTER TABLE `occupant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `chambres`
--
ALTER TABLE `chambres`
  ADD CONSTRAINT `chambres_ibfk_1` FOREIGN KEY (`id_batiment`) REFERENCES `batiments` (`id`);

--
-- Contraintes pour la table `douches`
--
ALTER TABLE `douches`
  ADD CONSTRAINT `douches_ibfk_1` FOREIGN KEY (`id_chambre`) REFERENCES `chambres` (`id`);

--
-- Contraintes pour la table `etat_lit`
--
ALTER TABLE `etat_lit`
  ADD CONSTRAINT `etat_lit_ibfk_1` FOREIGN KEY (`id_lit`) REFERENCES `lits` (`id`),
  ADD CONSTRAINT `etat_lit_ibfk_2` FOREIGN KEY (`id_occupant`) REFERENCES `occupant` (`id`);

--
-- Contraintes pour la table `lits`
--
ALTER TABLE `lits`
  ADD CONSTRAINT `lits_ibfk_1` FOREIGN KEY (`id_chambre`) REFERENCES `chambres` (`id`);

--
-- Contraintes pour la table `mois`
--
ALTER TABLE `mois`
  ADD CONSTRAINT `mois_ibfk_1` FOREIGN KEY (`id_lit`) REFERENCES `lits` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
