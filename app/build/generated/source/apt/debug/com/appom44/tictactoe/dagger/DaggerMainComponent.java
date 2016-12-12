package com.appom44.tictactoe.dagger;

import com.appom44.tictactoe.GameResultListener;
import com.appom44.tictactoe.PlayerStatDialogFragment;
import com.appom44.tictactoe.PlayerStatDialogFragment_MembersInjector;
import com.appom44.tictactoe.TicTacToeActivity;
import com.appom44.tictactoe.TicTacToeActivity_MembersInjector;
import com.appom44.tictactoe.entities.GamePlayed;
import com.appom44.tictactoe.entities.GamePlayer;
import com.appom44.tictactoe.entities.Player;
import com.appom44.tictactoe.persistence.IGameResultRepository;
import com.appom44.tictactoe.persistence.IPlayerRepository;
import com.appom44.tictactoe.persistence.IPlayerStatRepository;
import com.appom44.tictactoe.state.BoardModelStatePersistenceManager;
import com.appom44.tictactoe.state.PlayerStatePersistenceManager;
import com.j256.ormlite.dao.Dao;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerMainComponent implements MainComponent {
  private Provider<BoardModelStatePersistenceManager>
      provideBoardModelStatePeristenceManagerProvider;

  private Provider<PlayerStatePersistenceManager> providePlayerStatePersistenceManagerProvider;

  private Provider<Dao<Player, String>> providePlayerDaoProvider;

  private Provider<Dao<GamePlayed, Integer>> provideGamePlayedDaoProvider;

  private Provider<Dao<GamePlayer, Integer>> provideGamePlayerDaoProvider;

  private Provider<IGameResultRepository> provideGameResultRepositoryProvider;

  private Provider<GameResultListener> provideGameResultListenerProvider;

  private MembersInjector<TicTacToeActivity> ticTacToeActivityMembersInjector;

  private Provider<IPlayerRepository> providePlayerRepositoryProvider;

  private Provider<IPlayerStatRepository> providePlayerStatRepositoryProvider;

  private MembersInjector<PlayerStatDialogFragment> playerStatDialogFragmentMembersInjector;

  private DaggerMainComponent(Builder builder) {
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {

    this.provideBoardModelStatePeristenceManagerProvider =
        PortableModule_ProvideBoardModelStatePeristenceManagerFactory.create(
            builder.portableModule);

    this.providePlayerStatePersistenceManagerProvider =
        PortableModule_ProvidePlayerStatePersistenceManagerFactory.create(builder.portableModule);

    this.providePlayerDaoProvider =
        DoubleCheck.provider(DaoModule_ProvidePlayerDaoFactory.create(builder.daoModule));

    this.provideGamePlayedDaoProvider =
        DoubleCheck.provider(DaoModule_ProvideGamePlayedDaoFactory.create(builder.daoModule));

    this.provideGamePlayerDaoProvider =
        DoubleCheck.provider(DaoModule_ProvideGamePlayerDaoFactory.create(builder.daoModule));

    this.provideGameResultRepositoryProvider =
        RepositoryModule_ProvideGameResultRepositoryFactory.create(
            builder.repositoryModule,
            providePlayerDaoProvider,
            provideGamePlayedDaoProvider,
            provideGamePlayerDaoProvider);

    this.provideGameResultListenerProvider =
        PortableModule_ProvideGameResultListenerFactory.create(
            builder.portableModule, provideGameResultRepositoryProvider);

    this.ticTacToeActivityMembersInjector =
        TicTacToeActivity_MembersInjector.create(
            provideBoardModelStatePeristenceManagerProvider,
            providePlayerStatePersistenceManagerProvider,
            provideGameResultListenerProvider);

    this.providePlayerRepositoryProvider =
        RepositoryModule_ProvidePlayerRepositoryFactory.create(
            builder.repositoryModule, providePlayerDaoProvider);

    this.providePlayerStatRepositoryProvider =
        RepositoryModule_ProvidePlayerStatRepositoryFactory.create(
            builder.repositoryModule,
            provideGamePlayerDaoProvider,
            providePlayerRepositoryProvider);

    this.playerStatDialogFragmentMembersInjector =
        PlayerStatDialogFragment_MembersInjector.create(providePlayerStatRepositoryProvider);
  }

  @Override
  public void inject(TicTacToeActivity activity) {
    ticTacToeActivityMembersInjector.injectMembers(activity);
  }

  @Override
  public void inject(PlayerStatDialogFragment playerStatDialogFragment) {
    playerStatDialogFragmentMembersInjector.injectMembers(playerStatDialogFragment);
  }

  public static final class Builder {
    private PortableModule portableModule;

    private DaoModule daoModule;

    private RepositoryModule repositoryModule;

    private Builder() {}

    public MainComponent build() {
      if (portableModule == null) {
        this.portableModule = new PortableModule();
      }
      if (daoModule == null) {
        throw new IllegalStateException(DaoModule.class.getCanonicalName() + " must be set");
      }
      if (repositoryModule == null) {
        this.repositoryModule = new RepositoryModule();
      }
      return new DaggerMainComponent(this);
    }

    public Builder daoModule(DaoModule daoModule) {
      this.daoModule = Preconditions.checkNotNull(daoModule);
      return this;
    }

    public Builder repositoryModule(RepositoryModule repositoryModule) {
      this.repositoryModule = Preconditions.checkNotNull(repositoryModule);
      return this;
    }

    public Builder portableModule(PortableModule portableModule) {
      this.portableModule = Preconditions.checkNotNull(portableModule);
      return this;
    }
  }
}
