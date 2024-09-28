package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class EquipeServiceImpl implements IEquipeService{
	EquipeRepository equipeRepository;


	public List<Equipe> retrieveAllEquipes(){
	return  (List<Equipe>) equipeRepository.findAll();
	}
	public Equipe addEquipe(Equipe e){
		log.info("[+] Ajout d'une équipe ...");
		return (equipeRepository.save(e));
	}

	public  void deleteEquipe(Integer idEquipe){
		Equipe e=retrieveEquipe(idEquipe);
		equipeRepository.delete(e);
		log.info("[+] Suppression d'une équipe ...");
	}

	public Equipe retrieveEquipe(Integer equipeId){
		log.info("[+] Affichage d'une équipe ...");
		return equipeRepository.findById(equipeId).get();
	}

	public Equipe updateEquipe(Equipe e){
		log.info("[+] Mise à jour de l'équipe ...");
		return (equipeRepository.save(e));
	}

	public void evoluerEquipes(){
		List<Equipe> equipes = (List<Equipe>) equipeRepository.findAll();
		for (Equipe equipe : equipes) {
			if ((equipe.getNiveau().equals(Niveau.JUNIOR)) || (equipe.getNiveau().equals(Niveau.SENIOR))) {
				List<Etudiant> etudiants = (List<Etudiant>) equipe.getEtudiants();
				Integer nbEtudiantsAvecContratsActifs=0;
				for (Etudiant etudiant : etudiants) {
					Set<Contrat> contrats = etudiant.getContrats();
					//Set<Contrat> contratsActifs=null;
					for (Contrat contrat : contrats) {
						Date dateSysteme = new Date();
						long difference_In_Time = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();
						long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));
						if ((contrat.getArchive() == false) && (difference_In_Years > 1)) {
							//	contratsActifs.add(contrat);
							nbEtudiantsAvecContratsActifs++;
							break;
						}
						if (nbEtudiantsAvecContratsActifs >= 3) break;
					}
				}
					if (nbEtudiantsAvecContratsActifs >= 3){
						if (equipe.getNiveau().equals(Niveau.JUNIOR)){
							log.info("[+] Mise à jour du contrat de l'équipe en SENIOR...");
							equipe.setNiveau(Niveau.SENIOR);
							equipeRepository.save(equipe);
							break;
						}
						if (equipe.getNiveau().equals(Niveau.SENIOR)){
							log.info("[+] Mise à jour du contrat de l'équipe en EXPERT...");
							equipe.setNiveau(Niveau.EXPERT);
							equipeRepository.save(equipe);
							break;
						}
				}
			}

		}

	}
}
