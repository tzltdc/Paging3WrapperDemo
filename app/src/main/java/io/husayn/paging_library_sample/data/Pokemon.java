package io.husayn.paging_library_sample.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

@Entity(tableName = Pokemon.TABLE_NAME)
public class Pokemon {

  public static final String TABLE_NAME = "pokemon";

  @PrimaryKey
  @ColumnInfo(name = "id")
  public int id;

  @ColumnInfo(name = "name")
  public String name;

  public Pokemon(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public static final DiffUtil.ItemCallback<Pokemon> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Pokemon>() {
        @Override
        public boolean areItemsTheSame(@NonNull Pokemon oldPokemon, @NonNull Pokemon newPokemon) {
          return oldPokemon.id == newPokemon.id;
        }

        @Override
        public boolean areContentsTheSame(
            @NonNull Pokemon oldPokemon, @NonNull Pokemon newPokemon) {
          return oldPokemon.name.equals(newPokemon.name);
        }
      };
}
