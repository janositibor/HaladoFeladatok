package Week03.FootballTeams;

public class TeamPlayerService {
    TeamRepository teamRepository;
    PlayerRepository playerRepository;

    public TeamPlayerService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public void transferPlayer(long teamID, long playerID){
        Team team=teamRepository.findTeamByID(teamID);
        Player player=playerRepository.findPlayerByID(playerID);
        if(player.getTeam()==null){
            playerRepository.updatePlayerTeam(team.getId(),player.getId());
        }
        else if(player.getValue()<(20.0*team.getBudget())/100.0){
            teamRepository.decreaseBudgetTeam(team.getId(), player.getValue());
            playerRepository.updatePlayerTeam(team.getId(),player.getId());
        }
        else{
            throw new IllegalArgumentException("Team doesn't have enough money to buy this player!");
        }

    }
}
