
# Les fonctionnalités principales de l'application :

## ReservationController:
- deleteReservation(Long id): Supprime une réservation spécifique par id. <br>
- getAllReservations(): Récupère toutes les réservations, incluant les informations de la salle et de la réunion associées.<br>

## ReunionController
- obtenirMeilleureSalle(Long reunionId): Retourne la meilleure salle disponible pour une réunion donnée en fonction de ses besoins (capacité et équipements). <br>
- createReunion(ReunionDTO reunionDTO): Crée une nouvelle réunion et trouve la meilleure salle disponible pour cette réunion. Si une salle est trouvée, une réservation est créée. <br>
- deleteReunion(Long id): Supprime une réunion spécifique par ID. <br>
- getAllReunions(): Récupère toutes les réunions. <br>

## SalleController
- createSalle(SalleDTO salleDTO): Crée une nouvelle salle avec les informations fournies.<br>
- deleteSalle(Long id): Supprime une salle spécifique par ID. <br>
- getAllSalles(): Récupère toutes les salles. <br>
- getAllAvailableSalles(LocalTime heureDebut, LocalTime heureFin): Récupère toutes les salles disponibles pour une plage horaire spécifiée. <br>
- checkSalleDisponibilite(Long salleId, LocalTime heureDebut, LocalTime heureFin): Vérifie la disponibilité d'une salle spécifique pour une plage horaire donnée. <br>


# Logique de Filtrage pour la Sélection de Salles

## SalleService
- La méthode trouverMeilleureSalle(Reunion reunion) est responsable de la logique de filtrage et de sélection des salles. Voici les principaux filtres appliqués pour choisir la salle la plus appropriée : <br>

## Capacité :
- La salle doit avoir une capacité suffisante pour accueillir le nombre de personnes participant à la réunion. <br>
- Une salle ne doit pas avoir une capacité excessivement grande par rapport au nombre de participants pour éviter le gaspillage d'espace. <br>

## Équipements :
- La salle doit être équipée des dispositifs nécessaires pour le type de réunion. Par exemple, une réunion de type VC nécessitera une webcam et un écran. <br>
- Une salle avec des équipements supplémentaires non nécessaires pour la réunion sera évitée si une salle plus simple (et donc plus adaptée) est disponible. <br>

## Disponibilité :
- La salle doit être libre pendant toute la durée de la réunion. <br>
- Ces filtres garantissent que la salle choisie est la plus adaptée en termes de taille et d'équipements, tout en évitant le gaspillage de ressources. <br>

### Lors du démarrage de l'application, les réunions et les salles existantes dans le document sont chargées.
