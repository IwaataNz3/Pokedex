# Pendências (ações do Júlio) — Pokédex

Siga na ordem. Os passos 1 a 5 bastam para apresentar; o 6 (deploy) é a parte final da A44.

## 1. Criar a tabela no Oracle (SQL Developer) *(A40)*

```sql
CREATE TABLE DDD_POKEMONS (
    CODIGO NUMBER GENERATED ALWAYS AS IDENTITY,
    NUMERO NUMBER,
    NOME VARCHAR2(100),
    TIPO VARCHAR2(50),
    ALTURA NUMBER(5,2),
    PESO NUMBER(6,2),
    DATA_DA_CAPTURA DATE,
    PRIMARY KEY (CODIGO)
);
```

## 2. Variáveis de ambiente no IntelliJ *(A44, slide 13)*

O usuário e a senha não ficam mais no código: a `ConnectionFactory` lê das variáveis de ambiente.

1. Abra o projeto `pokedex` no IntelliJ e faça o **Sync Maven Changes**.
2. No topo, ao lado do ▶, clique em **PokedexApplication → Edit Configurations...**
3. Em **Environment variables**, cole a linha abaixo, trocando `SUA_SENHA` pela sua senha:

```
DB_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL;DB_USER=rm573723;DB_PASSWORD=SUA_SENHA
```

4. **OK** e rode o `PokedexApplication`.

> Se aparecer "Variáveis de ambiente do banco não configuradas!", foi este passo que ficou faltando.

## 3. Insomnia: collection **Pokedex** *(A40 a A43)*

Os JSONs estão prontos no `pokemons.txt`.

| Nome | Verbo | URL | Body | Esperado |
|---|---|---|---|---|
| save | POST | `http://localhost:8080/pokedex` | JSON de cadastro | **201** |
| findAll | GET | `http://localhost:8080/pokedex` | — | **200** + lista |
| findByCodigo | GET | `http://localhost:8080/pokedex/1` | — | **200**; código que não existe → **404** |
| update | PUT | `http://localhost:8080/pokedex/1` | JSON de atualização | **201** |
| delete | DELETE | `http://localhost:8080/pokedex/1` | — | **204**; repetir → **404** |

- [ ] Testar também o JSON inválido do `pokemons.txt` no save → **400** (Bean Validation).

## 4. Front-End *(A44)*

Com a API rodando no IntelliJ, abra um terminal na pasta `Documents\pokedex-front`:

```
npm run dev
```

Abra **http://localhost:5173**. Teste cadastrar, editar, excluir e buscar por código. Isso só funciona por causa do `CorsFilter`.

## 5. Build: gerar e rodar o .jar *(A44, slides 8 a 10)*

No terminal do IntelliJ, dentro da pasta `pokedex`:

```
.\mvnw clean -DskipTests package
```

Para rodar o `.jar`, o terminal também precisa das variáveis de ambiente (PowerShell):

```
$env:DB_URL="jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL"; $env:DB_USER="rm573723"; $env:DB_PASSWORD="SUA_SENHA"
java -jar target/pokedex-0.0.1-SNAPSHOT.jar
```

Ctrl+C encerra.

## 6. Deploy no Render *(A44, slides 12 a 19)*

O `Dockerfile` já está pronto na pasta `pokedex`.

1. Crie um repositório no GitHub e faça o push da pasta `pokedex` (comandos no slide 14).
2. Em render.com: **New → Web Service** → escolha o repositório → nome com o seu RM → plano **Free**.
3. Em **Environment Variables**, cadastre `DB_URL`, `DB_USER` e `DB_PASSWORD`.
4. **Deploy Web Service** e aguarde. Teste `https://SEU-SERVICO.onrender.com/pokedex`.
5. Para o front usar a API publicada, crie em `pokedex-front` um arquivo `.env` com:

```
VITE_API_URL=https://SEU-SERVICO.onrender.com/pokedex
```
