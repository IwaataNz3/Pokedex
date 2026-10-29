package br.com.fiap.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

// TO (Transfer Object): representa um Pokémon, ou seja, uma linha da tabela DDD_POKEMONS.
// É esta classe que o Spring converte de/para JSON nas requisições.
public class PokemonTO {
    // chave primária, gerada automaticamente pelo banco (IDENTITY)
    private Long codigo;
    // Bean Validation (A41): regras checadas pelo @Valid antes de salvar
    @NotNull
    @Positive
    private Long numero;            // número na Pokédex (ex.: Pikachu = 25)
    @NotBlank
    private String nome;
    @NotBlank
    private String tipo;
    @NotNull
    @Positive
    private Double altura;          // em metros
    @NotNull
    @Positive
    private Double peso;            // em kg
    @NotNull
    @Past
    private LocalDate dataDaCaptura; // precisa ser uma data no passado

    // construtor vazio: usado pelo Spring para montar o objeto a partir do JSON
    public PokemonTO() {
    }

    // construtor com parâmetros: facilita criar um Pokémon em uma linha
    public PokemonTO(Long codigo, Long numero, String nome, String tipo, Double altura, Double peso, LocalDate dataDaCaptura) {
        this.codigo = codigo;
        this.numero = numero;
        this.nome = nome;
        this.tipo = tipo;
        this.altura = altura;
        this.peso = peso;
        this.dataDaCaptura = dataDaCaptura;
    }

    // getters e setters: o Spring usa os getters para gerar o JSON de resposta
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public LocalDate getDataDaCaptura() {
        return dataDaCaptura;
    }

    public void setDataDaCaptura(LocalDate dataDaCaptura) {
        this.dataDaCaptura = dataDaCaptura;
    }
}
