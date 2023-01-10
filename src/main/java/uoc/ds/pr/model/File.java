package uoc.ds.pr.model;

import uoc.ds.pr.SportEvents4Club;

import java.time.LocalDate;

public class File {
    private final String fileId;
    private final String eventId;
    private int orgId;
    private String description;
    private SportEvents4Club.Type type;
    private byte resources;
    private int max;
    private LocalDate startDate;
    private LocalDate endDate;
    private SportEvents4Club.Status status;
    private LocalDate processedDate;
    private String refusalDescription;

    public File(String fileId, String eventId, int orgId, String description, SportEvents4Club.Type type, byte resources, int max, LocalDate startDate, LocalDate endDate) {
        this.fileId = fileId;
        this.eventId = eventId;
        setOrgId(orgId);
        setDescription(description);
        setType(type);
        setResources(resources);
        setMax(max);
        setStartDate(startDate);
        setEndDate(endDate);
        setStatus(SportEvents4Club.Status.PENDING);
        setProcessedDate(null);
        setRefusalDescription(null);
    }

    public String getFileId() {
        return fileId;
    }

    public String getEventId() {
        return eventId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SportEvents4Club.Type getType() {
        return type;
    }

    public void setType(SportEvents4Club.Type type) {
        this.type = type;
    }

    public byte getResources() {
        return resources;
    }

    public void setResources(byte resources) {
        this.resources = resources;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public SportEvents4Club.Status getStatus() {
        return status;
    }

    public void setStatus(SportEvents4Club.Status status) {
        this.status = status;
    }

    public LocalDate getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(LocalDate processedDate) {
        this.processedDate = processedDate;
    }

    public String getRefusalDescription() {
        return refusalDescription;
    }

    public void setRefusalDescription(String refusalDescription) {
        this.refusalDescription = refusalDescription;
    }
}
