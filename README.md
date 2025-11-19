#  Coral System Web

**Plataforma web Java (Servlets/JPA) para gerenciamento de coral musical, cobrindo membros, agenda e segurança via JWT.**

---

##  1. Visão Geral e Arquitetura

O **Coral System Web** é uma aplicação administrativa construída em Java. O projeto foi migrado de uma arquitetura desktop para uma arquitetura robusta de **Múltiplas Camadas (N-Tier)**, garantindo escalabilidade e segurança via padrões modernos do ecossistema Java (Jakarta EE).

### Estrutura em Camadas

| Camada | Padrão Utilizado | Propósito |
| :--- | :--- | :--- |
| **Persistência** | JPA (Hibernate) | Gerenciamento seguro de dados de Membros, Eventos e Presenças. |
| **Controle/API** | Servlets (Jakarta EE) | Recebe requisições HTTP, executa a lógica de negócios e roteia a aplicação. |
| **Segurança** | **JWT (JSON Web Token)** | Autenticação, controle de acesso e proteção das rotas do portal. |
| **Front-end** | JSP (JavaServer Pages) | Interface dinâmica e intuitiva para o usuário. |

## 2. Principais Funcionalidades Implementadas

O sistema atende a todos os requisitos do escopo original para a gestão administrativa do coral:

* **Portal de Acesso Seguro:** Acesso restrito e protegido. Utiliza o **Filtro de Autenticação** (`AutentFiltro`) com validação de tokens JWT e criptografia **BCrypt** para senhas.
* **Gestão de Membros:** Cadastro, edição e visualização de Coristas e Músicos, unificados em uma única entidade, com detalhes de voz ou instrumento (`Membro.java`).
* **Agenda de Eventos:** Registro e acompanhamento completo de Ensaios e Apresentações (`EventoServlet`).
* **Controle de Presenças:** Interface dedicada para marcar a presença (`PresencaServlet`) de cada membro em um evento específico, fornecendo a base para futuros relatórios.

##  3. Tecnologias Utilizadas

| Componente | Tecnologia | Versão/Detalhe |
| :--- | :--- | :--- |
| **Back-end Principal** | Java | **JDK 21** |
| **Web Framework** | Jakarta Servlets API | **6.0** |
| **Banco de Dados** | MySQL | Utilizado para persistência de dados. |
| **ORM** | Hibernate / JPA | Mapeamento Objeto-Relacional. |
| **Segurança (Hashing)** | BCrypt Library | Criptografia de senhas. |
| **Segurança (Token)** | JJWT (io.jsonwebtoken) | Geração e validação de tokens. |

---

*O projeto está pronto para ser implantado em qualquer servidor compatível com Jakarta EE 6.0, como o Apache Tomcat 10.1 ou superior.*
