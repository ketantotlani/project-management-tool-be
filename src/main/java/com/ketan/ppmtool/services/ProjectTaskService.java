package com.ketan.ppmtool.services;

import com.ketan.ppmtool.domain.Backlog;
import com.ketan.ppmtool.domain.Project;
import com.ketan.ppmtool.domain.ProjectTask;
import com.ketan.ppmtool.exceptions.ProjectNotFoundException;
import com.ketan.ppmtool.repositories.BacklogRespository;
import com.ketan.ppmtool.repositories.ProjectRepository;
import com.ketan.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRespository backlogRespository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){

            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();

            System.out.println(backlog);
            projectTask.setBacklog(backlog);

            Integer BacklogSequence = backlog.getPTSequence();
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            if(projectTask.getStatus() == ""||projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }

            if(projectTask.getPriority()==null || projectTask.getPriority()==0){
                projectTask.setPriority(3);
            }

            return projectTaskRepository.save(projectTask);

    }

    public Iterable<ProjectTask> findBacklogById(String id, String username){
        projectService.findProjectByIdentifier(id,username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){

        projectService.findProjectByIdentifier(backlog_id, username);

        //make sure we are searching on the right backlog
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' not found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '" + pt_id + "' does not exist in project: " + backlog_id);
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updateTask, String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);

        projectTask = updateTask;

        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id,pt_id,username);

        projectTaskRepository.delete(projectTask);
    }
}
