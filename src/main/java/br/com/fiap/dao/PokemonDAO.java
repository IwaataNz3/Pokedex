package br.com.fiap.dao;

import br.com.fiap.to.PokemonTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// DAO (Data Access Object): única classe que conversa com a tabela DDD_POKEMONS.
public class PokemonDAO {

    // SELECT de todos os Pokémons (A41)
    public ArrayList<PokemonTO> findAll() {
        ArrayList<PokemonTO> pokemons = new ArrayList<PokemonTO>();
        String sql = "select * from ddd_pokemons order by codigo";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            // executeQuery: usado para SELECT, devolve as linhas no ResultSet
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                // percorre linha por linha, montando um PokemonTO para cada uma
                while (rs.next()) {
                    PokemonTO pokemon = new PokemonTO();
                    pokemon.setCodigo(rs.getLong("codigo"));
                    pokemon.setNumero(rs.getLong("numero"));
                    pokemon.setNome(rs.getString("nome"));
                    pokemon.setTipo(rs.getString("tipo"));
                    pokemon.setAltura(rs.getDouble("altura"));
                    pokemon.setPeso(rs.getDouble("peso"));
                    pokemon.setDataDaCaptura(rs.getDate("data_da_captura").toLocalDate());
                    pokemons.add(pokemon);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return pokemons;
    }

    // SELECT de um Pokémon pelo código (A41)
    public PokemonTO findByCodigo(Long codigo) {
        PokemonTO pokemon = new PokemonTO();
        String sql = "select * from ddd_pokemons where codigo = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            // preenche o "?" do SQL com o código recebido
            ps.setLong(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pokemon.setCodigo(rs.getLong("codigo"));
                pokemon.setNumero(rs.getLong("numero"));
                pokemon.setNome(rs.getString("nome"));
                pokemon.setTipo(rs.getString("tipo"));
                pokemon.setAltura(rs.getDouble("altura"));
                pokemon.setPeso(rs.getDouble("peso"));
                pokemon.setDataDaCaptura(rs.getDate("data_da_captura").toLocalDate());
            } else {
                // nenhuma linha encontrada com esse código
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return pokemon;
    }

    // INSERT de um novo Pokémon (A40); o código não entra porque o banco gera sozinho
    public PokemonTO save(PokemonTO pokemon) {
        String sql = "insert into ddd_pokemons (numero, nome, tipo, altura, peso, data_da_captura) values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, pokemon.getNumero());
            ps.setString(2, pokemon.getNome());
            ps.setString(3, pokemon.getTipo());
            ps.setDouble(4, pokemon.getAltura());
            ps.setDouble(5, pokemon.getPeso());
            // converte LocalDate (Java) para Date (banco)
            ps.setDate(6, Date.valueOf(pokemon.getDataDaCaptura()));
            // executeUpdate: usado para INSERT/UPDATE/DELETE, devolve quantas linhas mudaram
            if (ps.executeUpdate() > 0) {
                return pokemon;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    // DELETE de um Pokémon pelo código (A42); true se apagou, false se não
    public boolean delete(Long codigo) {
        String sql = "delete from ddd_pokemons where codigo = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    // UPDATE de um Pokémon existente (A43); o último "?" é o código de quem será alterado
    public PokemonTO update(PokemonTO pokemon) {
        String sql = "update ddd_pokemons set numero=?, nome=?, tipo=?, altura=?, peso=?, data_da_captura=? where codigo=?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, pokemon.getNumero());
            ps.setString(2, pokemon.getNome());
            ps.setString(3, pokemon.getTipo());
            ps.setDouble(4, pokemon.getAltura());
            ps.setDouble(5, pokemon.getPeso());
            ps.setDate(6, Date.valueOf(pokemon.getDataDaCaptura()));
            ps.setLong(7, pokemon.getCodigo());
            if (ps.executeUpdate() > 0) {
                return pokemon;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }
}
