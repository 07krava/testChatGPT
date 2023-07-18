    create table messages (
    id bigint not null auto_increment,
    booking_id bigint, text varchar(255),
    timestamp datetime(6),
    recipient_id bigint,
    sender_id bigint, primary key (id));

     alter table messages add constraint FKhky628e8v09g8h9qg27jab05v foreign key (recipient_id) references user (id);
     alter table messages add constraint FKip9clvpi646rirksmm433wykx foreign key (sender_id) references user (id);