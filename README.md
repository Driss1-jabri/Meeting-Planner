#Fonctionnalités Intégrées
ReservationController
deleteReservation(Long id): Supprime une réservation spécifique par ID.
getAllReservations(): Récupère toutes les réservations sous forme de ReservationDTO, incluant les informations de la salle et de la réunion associées.
ReunionController
obtenirMeilleureSalle(Long reunionId): Retourne la meilleure salle disponible pour une réunion donnée en fonction de ses besoins (capacité et équipements).
createReunion(ReunionDTO reunionDTO): Crée une nouvelle réunion et trouve la meilleure salle disponible pour cette réunion. Si une salle est trouvée, une réservation est créée et retournée sous forme de ReservationDTO.
deleteReunion(Long id): Supprime une réunion spécifique par ID.
getAllReunions(): Récupère toutes les réunions sous forme de ReunionDTO.
SalleController
createSalle(SalleDTO salleDTO): Crée une nouvelle salle avec les informations fournies.
deleteSalle(Long id): Supprime une salle spécifique par ID.
getAllSalles(): Récupère toutes les salles sous forme de SalleDTO.
getAllAvailableSalles(LocalTime heureDebut, LocalTime heureFin): Récupère toutes les salles disponibles pour une plage horaire spécifiée.
checkSalleDisponibilite(Long salleId, LocalTime heureDebut, LocalTime heureFin): Vérifie la disponibilité d'une salle spécifique pour une plage horaire donnée.
Logique de Filtrage pour la Sélection de Salles
SalleService
La méthode trouverMeilleureSalle(Reunion reunion) est responsable de la logique de filtrage et de sélection des salles. Voici les principaux filtres appliqués pour choisir la salle la plus appropriée :

Capacité :

La salle doit avoir une capacité suffisante pour accueillir le nombre de personnes participant à la réunion.
Une salle ne doit pas avoir une capacité excessivement grande par rapport au nombre de participants pour éviter le gaspillage d'espace.
Équipements :

La salle doit être équipée des dispositifs nécessaires pour le type de réunion. Par exemple, une réunion de type VC (visio-conférence) nécessitera une webcam et un écran.
Une salle avec des équipements supplémentaires non nécessaires pour la réunion sera évitée si une salle plus simple (et donc plus adaptée) est disponible.
Disponibilité :

La salle doit être libre pendant toute la durée de la réunion.
Ces filtres garantissent que la salle choisie est la plus adaptée en termes de taille et d'équipements, tout en évitant le gaspillage de ressources.
