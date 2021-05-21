alter table membership
    add status integer;

update membership set status = 1;