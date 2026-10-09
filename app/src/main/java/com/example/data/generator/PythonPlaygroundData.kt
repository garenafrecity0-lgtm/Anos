package com.example.data.generator

import com.example.data.model.PythonCheatSheetItem
import com.example.data.model.PythonLevel
import com.example.data.model.PythonPlaygroundSnippet

object PythonPlaygroundData {

    val snippets: List<PythonPlaygroundSnippet> = listOf(
        PythonPlaygroundSnippet(
            id = "play_01",
            title = "1. Hello World & Calculs de Base",
            level = PythonLevel.DEBUTANT,
            description = "Affichage formaté, variables et calculs élémentaires.",
            code = """
# Bienvenue sur le Bac à Sable Anos py
nom = "Développeur"
version = 3.12

print(f"Bonjour {nom} ! Bienvenue sur Python {version}")

# Calculs
rayon = 5
aire_cercle = 3.14159 * (rayon ** 2)
print(f"L'aire d'un cercle de rayon {rayon} est : {aire_cercle:.2f}")
            """.trimIndent(),
            simulatedOutput = """
Bonjour Développeur ! Bienvenue sur Python 3.12
L'aire d'un cercle de rayon 5 est : 78.54
            """.trimIndent()
        ),
        PythonPlaygroundSnippet(
            id = "play_02",
            title = "2. Algorithme de Fibonacci (Générateur)",
            level = PythonLevel.EXPERT,
            description = "Génération paresseuse de la suite avec yield.",
            code = """
def fibonacci(n):
    a, b = 0, 1
    for _ in range(n):
        yield a
        a, b = b, a + b

print("Les 10 premiers termes de Fibonacci :")
for val in fibonacci(10):
    print(val, end=" -> ")
print("Fin !")
            """.trimIndent(),
            simulatedOutput = """
Les 10 premiers termes de Fibonacci :
0 -> 1 -> 1 -> 2 -> 3 -> 5 -> 8 -> 13 -> 21 -> 34 -> Fin !
            """.trimIndent()
        ),
        PythonPlaygroundSnippet(
            id = "play_03",
            title = "3. Décorateur de Mesure de Temps",
            level = PythonLevel.EXPERT,
            description = "Mesurez la durée d'exécution d'une fonction avec un décorateur.",
            code = """
import time
from functools import wraps

def benchmark(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        t0 = time.time()
        res = func(*args, **kwargs)
        t1 = time.time()
        print(f"[{func.__name__}] Exécuté en {(t1-t0)*1000:.3f} ms")
        return res
    return wrapper

@benchmark
def somme_carres(limite):
    return sum(x ** 2 for x in range(limite))

total = somme_carres(50_000)
print(f"Somme calculée : {total}")
            """.trimIndent(),
            simulatedOutput = """
[somme_carres] Exécuté en 4.215 ms
Somme calculée : 41665416675000
            """.trimIndent()
        ),
        PythonPlaygroundSnippet(
            id = "play_04",
            title = "4. Classe Compte Bancaire & @property",
            level = PythonLevel.AVANCE,
            description = "Modélisation objet avec encapsulation, getter et validation.",
            code = """
class CompteBancaire:
    def __init__(self, titulaire, solde_initial=0.0):
        self.titulaire = titulaire
        self._solde = float(solde_initial)

    @property
    def solde(self):
        return self._solde

    def deposer(self, montant):
        if montant <= 0:
            raise ValueError("Le montant doit être positif.")
        self._solde += montant
        print(f"💰 Dépôt de {montant}€ effectué.")

    def retirer(self, montant):
        if montant > self._solde:
            print("❌ Solde insuffisant !")
            return False
        self._solde -= montant
        print(f"💸 Retrait de {montant}€ validé.")
        return True

client = CompteBancaire("Anos", 250.0)
client.deposer(100.0)
client.retirer(50.0)
print(f"Solde final de {client.titulaire} : {client.solde}€")
            """.trimIndent(),
            simulatedOutput = """
💰 Dépôt de 100.0€ effectué.
💸 Retrait de 50.0€ validé.
Solde final de Anos : 300.0€
            """.trimIndent()
        ),
        PythonPlaygroundSnippet(
            id = "play_05",
            title = "5. Compréhensions & Filtrage Avancé",
            level = PythonLevel.INTERMEDIAIRE_1,
            description = "Transformation et filtrage rapide de listes et dictionnaires.",
            code = """
nombres = list(range(1, 21))

# Filtrer pairs et calculer les cubes
cubes_pairs = [x ** 3 for x in nombres if x % 2 == 0]
print("Cubes des nombres pairs de 1 à 20 :", cubes_pairs)

# Dictionnaire de longueurs
mots = ["Python", "Intelligence", "Artificielle", "Anos", "Code"]
longueurs = {mot: len(mot) for mot in mots}
print("Dictionnaire de longueurs :", longueurs)
            """.trimIndent(),
            simulatedOutput = """
Cubes des nombres pairs de 1 à 20 : [8, 64, 216, 512, 1000, 1728, 2744, 4096, 5832, 8000]
Dictionnaire de longueurs : {'Python': 6, 'Intelligence': 12, 'Artificielle': 12, 'Anos': 4, 'Code': 4}
            """.trimIndent()
        ),
        PythonPlaygroundSnippet(
            id = "play_06",
            title = "6. Simulation de Tâches Asynchrones (asyncio)",
            level = PythonLevel.EXPERT,
            description = "Lancement de requêtes parallèles non-bloquantes avec asyncio.",
            code = """
import asyncio

async def fetch_api(endpoint, delay):
    print(f"🚀 Début appel vers '{endpoint}'...")
    await asyncio.sleep(delay)
    print(f"📦 Réponse reçue de '{endpoint}' !")
    return f"Status 200 - {endpoint}"

async def main():
    print("--- Démarrage de l'Event Loop ---")
    taches = [
        fetch_api("/api/users", 0.1),
        fetch_api("/api/products", 0.2),
        fetch_api("/api/orders", 0.15)
    ]
    reponses = await asyncio.gather(*taches)
    print("--- Toutes les réponses sont là ! ---")
    for r in reponses:
        print("  •", r)

# asyncio.run(main())
print("Architecture asynchrone prête à l'emploi.")
            """.trimIndent(),
            simulatedOutput = """
--- Démarrage de l'Event Loop ---
🚀 Début appel vers '/api/users'...
🚀 Début appel vers '/api/products'...
🚀 Début appel vers '/api/orders'...
📦 Réponse reçue de '/api/users' !
📦 Réponse reçue de '/api/orders' !
📦 Réponse reçue de '/api/products' !
--- Toutes les réponses sont là ! ---
  • Status 200 - /api/users
  • Status 200 - /api/products
  • Status 200 - /api/orders
            """.trimIndent()
        )
    )

    val cheatSheetItems: List<PythonCheatSheetItem> = listOf(
        PythonCheatSheetItem(
            id = "cs_01",
            category = "Séquences & Slicing",
            title = "Découpage de listes (Slicing)",
            code = "s[start:stop:step]\ns[::-1]  # Inversion totale\ns[1:5]   # Indices 1 à 4 inclus\ns[:3]    # Les 3 premiers\ns[-3:]   # Les 3 derniers",
            description = "Extrait des sous-portions d'une chaîne ou liste sans altérer l'originale."
        ),
        PythonCheatSheetItem(
            id = "cs_02",
            category = "Dictionnaires",
            title = "Méthodes Clés de Dictionnaires",
            code = "d.get('cle', defaut)   # Accès sécurisé\nd.keys(), d.values()   # Vues clés / valeurs\nd.items()              # Paires (clé, val)\nd.update({'autre': 1}) # Fusion",
            description = "Gérez vos dictionnaires en temps constant O(1)."
        ),
        PythonCheatSheetItem(
            id = "cs_03",
            category = "Fonctions Intégrées",
            title = "enumerate() & zip()",
            code = "# Indice + Élément :\nfor i, val in enumerate(liste, 1):\n    print(i, val)\n\n# Fusion en parallèle :\nfor a, b in zip(liste_a, liste_b):\n    print(a, b)",
            description = "Outils élégants pour remplacer les compteurs d'indices manuels."
        ),
        PythonCheatSheetItem(
            id = "cs_04",
            category = "Gestion d'Erreurs",
            title = "Structure try / except / else / finally",
            code = "try:\n    calcul = 10 / n\nexcept ZeroDivisionError as e:\n    print('Erreur:', e)\nelse:\n    print('Succès:', calcul)\nfinally:\n    nettoyer_ressources()",
            description = "Protection complète contre les arrêts inopinés du programme."
        ),
        PythonCheatSheetItem(
            id = "cs_05",
            category = "POO",
            title = "Méthodes Spéciales Dunder indispensables",
            code = "class MonObjet:\n    def __init__(self): pass\n    def __str__(self): return 'Lisible'\n    def __repr__(self): return 'Tech'\n    def __len__(self): return 10\n    def __eq__(self, other): return True",
            description = "Connecte vos classes aux opérateurs natifs de Python."
        ),
        PythonCheatSheetItem(
            id = "cs_06",
            category = "Expert",
            title = "Décorateur de base",
            code = "from functools import wraps\n\ndef mon_decorateur(f):\n    @wraps(f)\n    def wrapper(*args, **kwargs):\n        # Avant\n        res = f(*args, **kwargs)\n        # Après\n        return res\n    return wrapper",
            description = "Modèle officiel pour créer des décorateurs réutilisables."
        ),
        PythonCheatSheetItem(
            id = "cs_07",
            category = "Expert",
            title = "Générateur simple (yield)",
            code = "def compte_a_rebours(n):\n    while n > 0:\n        yield n\n        n -= 1\n\nfor x in compte_a_rebours(5):\n    print(x)",
            description = "Génère les valeurs à la volée avec une empreinte mémoire minimale O(1)."
        )
    )
}
