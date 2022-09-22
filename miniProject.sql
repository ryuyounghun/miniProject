-- 밑에 INSERT문 몇개 만들어 두었습니다.

-- 회원 테이블
CREATE TABLE MEMBERS(
    MEMBERID VARCHAR2(10) PRIMARY KEY,
    MEMBERPW VARCHAR2(100) NOT NULL,
    MEMBERNAME VARCHAR2(30) NOT NULL,
    PHONE VARCHAR2(20) NOT NULL,
    BIRTH DATE,
    SEX VARCHAR2(20),
    ADDRESS VARCHAR2(1000),
    
    ENABLED NUMBER(1) DEFAULT 1 CHECK(ENABLED = 0 OR ENABLED = 1),
    ROLENAME VARCHAR2(10) DEFAULT 'ROLE_USER' CHECK(ROLENAME = 'ROLE_ADMIN' OR ROLENAME = 'ROLE_USER')
);
-- 상품 테이블
create table item(
    ITEMNUM NUMBER PRIMARY KEY,
    ITEMNAME VARCHAR2(30) NOT NULL,
    ITEMPRICE NUMBER(10) NOT NULL,
    ITEMCATEGORY VARCHAR2(100),
    ITEMIMAGE VARCHAR2(100),
    ITEMCONTENT VARCHAR2(1000)
    
    );
-- 주문정보 테이블  
create table order_table(
    memberid varchar2(10) references members(memberid) on delete cascade NOT NULL,
    ORDERNUM NUMBER references item(itemnum) on delete cascade NOT NULL,
    ORDERNAME VARCHAR2(30) NOT NULL,
    ORDERPRICE NUMBER(10) NOT NULL,
    ORDERIMAGE VARCHAR2(100),
    QUANTITY NUMBER(3) default 1
);
-- 게시판 테이블
CREATE TABLE NOTICE(
    NOTICENUM     NUMBER PRIMARY KEY,
    TITLE        VARCHAR2(500) NOT NULL,
    CONTENT      VARCHAR2(2000) NOT NULL,
    VIEWCOUNT    NUMBER DEFAULT 0,
    INPUTDATE    DATE DEFAULT SYSDATE,
    
    ORIGINALFILE VARCHAR2(200),
    SAVEDFILE VARCHAR2(200)
);

create sequence NOTICE_SEQ; -- 게시판 번호 시퀀스
CREATE SEQUENCE ITEM_SEQ1;  -- 상품 번호 시퀀스

-------------------------------------------------------------------------------------------------
-- 상품을 보려면 관리자 페이지에서 상품 등록을 해야해서 insert문을 미리 만들어 두었습니다.

-- 관리자계정 생성 업데이트
UPDATE MEMBERS
SET ROLENAME = 'ROLE_ADMIN'
WHERE MEMBERID = '';

INSERT INTO ITEM(ITEMNUM, ITEMNAME, ITEMPRICE, ITEMCATEGORY, ITEMIMAGE, ITEMCONTENT)
VALUES(ITEM_SEQ1.nextval, '버거1', '3000', '1', '/images/burger1.png', '그냥 햄버거');

INSERT INTO ITEM(ITEMNUM, ITEMNAME, ITEMPRICE, ITEMCATEGORY, ITEMIMAGE, ITEMCONTENT)
VALUES(ITEM_SEQ1.nextval, '버거1세트', '6000', '2', '/images/burger1set.png', '그냥 햄버거의 세트');

INSERT INTO ITEM(ITEMNUM, ITEMNAME, ITEMPRICE, ITEMCATEGORY, ITEMIMAGE, ITEMCONTENT)
VALUES(ITEM_SEQ1.nextval, '감자튀김', '3000', '3', '/images/fries.png', '맛있는 감자튀김');

INSERT INTO ITEM(ITEMNUM, ITEMNAME, ITEMPRICE, ITEMCATEGORY, ITEMIMAGE, ITEMCONTENT)
VALUES(ITEM_SEQ1.nextval, '코카콜라', '1500', '4', '/images/coke1.png', '맛있는 콜라');

commit;