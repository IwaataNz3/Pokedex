package br.com.fiap.resource;

import br.com.fiap.bo.PokemonBO;
import br.com.fiap.to.PokemonTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Resource (controller): recebe as requisições HTTP e devolve as respostas em JSON.
@RestController                 // classe que responde requisições REST
@RequestMapping("/pokedex")     // endereço base: http://localhost:8080/pokedex
public class PokemonResource {
    private PokemonBO pokemonBO = new PokemonBO();

    // GET /pokedex -> lista todos (A39/A41)
    @GetMapping
    public ResponseEntity<List<PokemonTO>> findAll() {
        List<PokemonTO> pokemons = pokemonBO.findAll();
        if (pokemons != null) {
            return ResponseEntity.status(HttpStatus.OK).body(pokemons);                // 200
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pokemons);         // 404
        }
    }

    // GET /pokedex/{codigo} -> busca um pelo código (A41)
    // @PathVariable pega o valor que veio na URI e coloca na variável "codigo"
    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        PokemonTO pokemon = pokemonBO.findByCodigo(codigo);
        if (pokemon != null) {
            return ResponseEntity.status(HttpStatus.OK).body(pokemon);                 // 200
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pokémon não encontrado!"); // 404
        }
    }

    // POST /pokedex -> cadastra (A40)
    // @RequestBody: o JSON do corpo vira um PokemonTO | @Valid: aplica o Bean Validation (A41)
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid PokemonTO pokemon) {
        try {
            PokemonTO resultado = pokemonBO.save(pokemon);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);     // 201
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar pokémon"); // 400
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar pokémon"); // 400
        }
    }

    // DELETE /pokedex/{codigo} -> apaga (A42)
    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        if (pokemonBO.delete(codigo)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)                        // 204
                    .body("Pokémon deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)                         // 404
                    .body("Pokémon não encontrado!");
        }
    }

    // PUT /pokedex/{codigo} -> atualiza (A43)
    // recebe o código pela URI e os dados novos pelo corpo (JSON)
    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid PokemonTO pokemon) {
        try {
            // junta o código da URI com os dados do JSON, para o DAO saber quem atualizar
            pokemon.setCodigo(codigo);
            PokemonTO resultado = pokemonBO.update(pokemon);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);     // 201
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar pokémon"); // 400
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar pokémon"); // 400
        }
    }
}
