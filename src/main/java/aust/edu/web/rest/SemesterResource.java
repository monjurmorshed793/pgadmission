package aust.edu.web.rest;

import aust.edu.domain.Semester;
import aust.edu.service.SemesterService;
import aust.edu.web.rest.errors.BadRequestAlertException;
import aust.edu.service.dto.SemesterCriteria;
import aust.edu.service.SemesterQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link aust.edu.domain.Semester}.
 */
@RestController
@RequestMapping("/api")
public class SemesterResource {

    private final Logger log = LoggerFactory.getLogger(SemesterResource.class);

    private static final String ENTITY_NAME = "semester";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SemesterService semesterService;

    private final SemesterQueryService semesterQueryService;

    public SemesterResource(SemesterService semesterService, SemesterQueryService semesterQueryService) {
        this.semesterService = semesterService;
        this.semesterQueryService = semesterQueryService;
    }

    /**
     * {@code POST  /semesters} : Create a new semester.
     *
     * @param semester the semester to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new semester, or with status {@code 400 (Bad Request)} if the semester has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/semesters")
    public ResponseEntity<Semester> createSemester(@Valid @RequestBody Semester semester) throws URISyntaxException {
        log.debug("REST request to save Semester : {}", semester);
        if (semester.getId() != null) {
            throw new BadRequestAlertException("A new semester cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Semester result = semesterService.save(semester);
        return ResponseEntity.created(new URI("/api/semesters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /semesters} : Updates an existing semester.
     *
     * @param semester the semester to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated semester,
     * or with status {@code 400 (Bad Request)} if the semester is not valid,
     * or with status {@code 500 (Internal Server Error)} if the semester couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/semesters")
    public ResponseEntity<Semester> updateSemester(@Valid @RequestBody Semester semester) throws URISyntaxException {
        log.debug("REST request to update Semester : {}", semester);
        if (semester.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Semester result = semesterService.save(semester);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, semester.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /semesters} : get all the semesters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of semesters in body.
     */
    @GetMapping("/semesters")
    public ResponseEntity<List<Semester>> getAllSemesters(SemesterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Semesters by criteria: {}", criteria);
        Page<Semester> page = semesterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /semesters/count} : count all the semesters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/semesters/count")
    public ResponseEntity<Long> countSemesters(SemesterCriteria criteria) {
        log.debug("REST request to count Semesters by criteria: {}", criteria);
        return ResponseEntity.ok().body(semesterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /semesters/:id} : get the "id" semester.
     *
     * @param id the id of the semester to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the semester, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/semesters/{id}")
    public ResponseEntity<Semester> getSemester(@PathVariable Long id) {
        log.debug("REST request to get Semester : {}", id);
        Optional<Semester> semester = semesterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(semester);
    }

    /**
     * {@code DELETE  /semesters/:id} : delete the "id" semester.
     *
     * @param id the id of the semester to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/semesters/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        log.debug("REST request to delete Semester : {}", id);
        semesterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
