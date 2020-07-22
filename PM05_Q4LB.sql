USE `CovidifyUSA`;
SET SQL_SAFE_UPDATES = 0;


DROP TABLE IF EXISTS `CovidifyUSA`.`InternationalCovidByDate`;
DROP TABLE IF EXISTS `CovidifyUSA`.`InternationalCovidByDateStaging`;
DROP TABLE IF EXISTS `CovidifyUSA`.`Country`;
DROP TABLE IF EXISTS `CovidifyUSA`.`InternationalCovidByDateStagingP1AF`;
DROP TABLE IF EXISTS `CovidifyUSA`.`InternationalCovidByDateStagingP2GZ`;
DROP TABLE IF EXISTS `CovidifyUSA`.`HighestCasesInternational`;
DROP TABLE IF EXISTS `CovidifyUSA`.`HighestCasesNational`;

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`InternationalCovidByDateStagingP1AF` (
`Date` DATE,
`World`TEXT,`Afghanistan`TEXT,`Albania`TEXT,`Algeria`TEXT,`Andorra`TEXT,`Angola`TEXT,
`Anguilla`TEXT,`Antigua and Barbuda`TEXT,`Argentina`TEXT,`Armenia`TEXT,`Aruba`TEXT,
`Australia`TEXT,`Austria`TEXT,`Azerbaijan`TEXT,`Bahamas`TEXT,`Bahrain`TEXT,`Bangladesh`TEXT,
`Barbados`TEXT,`Belarus`TEXT,`Belgium`TEXT,`Belize`TEXT,`Benin`TEXT,`Bermuda`TEXT,`Bhutan`TEXT,
`Bolivia`TEXT,`Bonaire Sint Eustatius and Saba`TEXT,`Bosnia and Herzegovina`TEXT,`Botswana`TEXT,
`Brazil`TEXT,`British Virgin Islands`TEXT,`Brunei`TEXT,`Bulgaria`TEXT,`Burkina Faso`TEXT,`Burundi`TEXT,
`Cambodia`TEXT,`Cameroon`TEXT,`Canada`TEXT,`Cape Verde`TEXT,`Cayman Islands`TEXT,
`Central African Republic`TEXT,`Chad`TEXT,`Chile`TEXT,`China`TEXT,`Colombia`TEXT,`Comoros`TEXT,
`Congo`TEXT,`Costa Rica`TEXT,`Cote d'Ivoire`TEXT,`Croatia`TEXT,`Cuba`TEXT,`Curacao`TEXT,`Cyprus`TEXT,
`Czech Republic`TEXT,`Democratic Republic of Congo`TEXT,`Denmark`TEXT,`Djibouti`TEXT,`Dominica`TEXT,
`Dominican Republic`TEXT,`Ecuador`TEXT,`Egypt`TEXT,`El Salvador`TEXT,`Equatorial Guinea`TEXT,`Eritrea`TEXT,
`Estonia`TEXT,`Ethiopia`TEXT,`Faeroe Islands`TEXT,`Falkland Islands`TEXT,`Fiji`TEXT,`Finland`TEXT,
`France`TEXT,`French Polynesia`TEXT);


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`InternationalCovidByDateStagingP2GZ` (
`Date` DATE,
`Gabon`TEXT,`Gambia`TEXT,`Georgia`TEXT,`Germany`TEXT,`Ghana`TEXT,`Gibraltar`TEXT,`Greece`TEXT,
`Greenland`TEXT,`Grenada`TEXT,`Guam`TEXT,`Guatemala`TEXT,`Guernsey`TEXT,`Guinea`TEXT,`Guinea-Bissau`TEXT,
`Guyana`TEXT,`Haiti`TEXT,`Honduras`TEXT,`Hungary`TEXT,`Iceland`TEXT,`India`TEXT,`Indonesia`TEXT,
`International`TEXT,`Iran`TEXT,`Iraq`TEXT,`Ireland`TEXT,`Isle of Man`TEXT,`Israel`TEXT,`Italy`TEXT,
`Jamaica`TEXT,`Japan`TEXT,`Jersey`TEXT,`Jordan`TEXT,`Kazakhstan`TEXT,`Kenya`TEXT,`Kosovo`TEXT,`Kuwait`TEXT,
`Kyrgyzstan`TEXT,`Laos`TEXT,`Latvia`TEXT,`Lebanon`TEXT,`Lesotho`TEXT,`Liberia`TEXT,`Libya`TEXT,
`Liechtenstein`TEXT,`Lithuania`TEXT,`Luxembourg`TEXT,`Macedonia`TEXT,`Madagascar`TEXT,`Malawi`TEXT,
`Malaysia`TEXT,`Maldives`TEXT,`Mali`TEXT,`Malta`TEXT,`Mauritania`TEXT,`Mauritius`TEXT,`Mexico`TEXT,
`Moldova`TEXT,`Monaco`TEXT,`Mongolia`TEXT,`Montenegro`TEXT,`Montserrat`TEXT,`Morocco`TEXT,
`Mozambique`TEXT,`Myanmar`TEXT,`Namibia`TEXT,`Nepal`TEXT,`Netherlands`TEXT,`New Caledonia`TEXT,
`New Zealand`TEXT,`Nicaragua`TEXT,`Niger`TEXT,`Nigeria`TEXT,`Northern Mariana Islands`TEXT,`Norway`TEXT,
`Oman`TEXT,`Pakistan`TEXT,`Palestine`TEXT,`Panama`TEXT,`Papua New Guinea`TEXT,`Paraguay`TEXT,`Peru`TEXT,
`Philippines`TEXT,`Poland`TEXT,`Portugal`TEXT,`Puerto Rico`TEXT,`Qatar`TEXT,`Romania`TEXT,`Russia`TEXT,
`Rwanda`TEXT,`Saint Kitts and Nevis`TEXT,`Saint Lucia`TEXT,`Saint Vincent and the Grenadines`TEXT,
`San Marino`TEXT,`Sao Tome and Principe`TEXT,`Saudi Arabia`TEXT,`Senegal`TEXT,`Serbia`TEXT,
`Seychelles`TEXT,`Sierra Leone`TEXT,`Singapore`TEXT,`Sint Maarten (Dutch part)`TEXT,`Slovakia`TEXT,
`Slovenia`TEXT,`Somalia`TEXT,`South Africa`TEXT,`South Korea`TEXT,`South Sudan`TEXT,`Spain`TEXT,
`Sri Lanka`TEXT,`Sudan`TEXT,`Suriname`TEXT,`Swaziland`TEXT,`Sweden`TEXT,`Switzerland`TEXT,`Syria`TEXT,
`Taiwan`TEXT,`Tajikistan`TEXT,`Tanzania`TEXT,`Thailand`TEXT,`Timor`TEXT,`Togo`TEXT,
`Trinidad and Tobago`TEXT,`Tunisia`TEXT,`Turkey`TEXT,`Turks and Caicos Islands`TEXT,
`Uganda`TEXT,`Ukraine`TEXT,`United Arab Emirates`TEXT,`United Kingdom`TEXT,`United States`TEXT,
`United States Virgin Islands`TEXT,`Uruguay`TEXT,`Uzbekistan`TEXT,`Vatican`TEXT,`Venezuela`TEXT,
`Vietnam`TEXT,`Western Sahara`TEXT,`Yemen`TEXT,`Zambia`TEXT,`Zimbabwe`TEXT);


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`InternationalCovidByDateStaging` (  
`InternationalCovidByDateStagingKey` INT NOT NULL AUTO_INCREMENT,  
`CountryName` TEXT,  `Date` DATE,  `CovidCases` INT NULL  ,
PRIMARY KEY (`InternationalCovidByDateStagingKey`))
ENGINE = InnoDB;  

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`Country` (  
`CountryKey` INT NOT NULL AUTO_INCREMENT,  
`CountryName` TEXT,
PRIMARY KEY (`CountryKey`))
ENGINE = InnoDB;  


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`InternationalCovidByDate` (
  `InternationalCovidByDateKey` INT NOT NULL AUTO_INCREMENT,
  `CountryFKey` INT NOT NULL,
   `Date` DATE,  `CovidCases` INT NULL  ,
  PRIMARY KEY (`InternationalCovidByDateKey`),
  INDEX `CountryFKey_idx` (`CountryFKey` ASC),
  CONSTRAINT `CountryFKey`
    FOREIGN KEY (`CountryFKey`)
    REFERENCES `CovidifyUSA`.`Country` (`CountryKey`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;



LOAD DATA INFILE '/Users/lilybessette/eclipse-workspace/Covidify-USA/DATA/total_cases.csv' 
INTO TABLE `CovidifyUSA`.`InternationalCovidByDateStagingP1AF` 
FIELDS TERMINATED BY ',' ENCLOSED BY '"'LINES TERMINATED BY '\n' IGNORE 1 ROWS(
@date,@world,@afghanistan,@albania,@algeria,@andorra,@angola,@anguilla,@antiguabarbuda,
@argentina,@armenia,@aruba,@australia,@austria,@azerbaijan,@bahamas,@bahrain,@bangladesh,
@barbados,@belarus,@belgium,@belize,@benin,@bermuda,@bhutan,@bolivia,@bonaire,@bosnia,
@botswana,@brazil,@british,@brunei,@bulgaria,@burkinaFaso,@burundi,@cambodia,@cameroon,
@canada,@capeVerde,@caymanIslands,@centralAfricanRepublic,@chad,@chile,@china,@colombia,
@comoros,@congo,@costaRica,@cotedIvoire,@croatia,@cuba,@curacao,@cyprus,@czechRepublic,
@democraticRepubCongo,@denmark,@djibouti,@dominica,@dominicanRepublic,@ecuador,@egypt,
@elSalvador,@equatorialGuinea,@eritrea,@estonia,@ethiopia,@faeroeIslands,@falklandIslands,
@fiji,@finland,@france,@frenchPolynesia,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy)
SET
`Date`=@date, 
`World`=@world,`Afghanistan`=@afghanistan,`Albania`=@albania,`Algeria`=@algeria,`Andorra`=@andorra,
`Angola`=@angola,`Anguilla`=@anguilla,`Antigua and Barbuda`=@antiguabarbuda,`Argentina`=@argentina,
`Armenia`=@armenia,`Aruba`=@aruba,`Australia`=@australia,`Austria`=@austria,`Azerbaijan`=@azerbaijan,
`Bahamas`=@bahamas,`Bahrain`=@bahrain,`Bangladesh`=@bangladesh,`Barbados`=@barbados,`Belarus`=@belarus,
`Belgium`=@belgium,`Belize`=@belize,`Benin`=@benin,`Bermuda`=@bermuda,`Bhutan`=@bhutan,`Bolivia`=@bolivia,
`Bonaire Sint Eustatius and Saba`=@bonaire,`Bosnia and Herzegovina`=@bosnia,`Botswana`=@botswana,
`Brazil`=@brazil,`British Virgin Islands`=@british,`Brunei`=@brunei,`Bulgaria`=@bulgaria,
`Burkina Faso`=@burkinaFaso,`Burundi`=@burundi,`Cambodia`=@cambodia,`Cameroon`=@cameroon,`Canada`=@canada,
`Cape Verde`=@capeVerde,`Cayman Islands`=@caymanIslands,`Central African Republic`=@centralAfricanRepublic,
`Chad`=@chad,`Chile`=@chile,`China`=@china,`Colombia`=@colombia,`Comoros`=@comoros,`Congo`=@congo,
`Costa Rica`=@costaRica,`Cote d'Ivoire`=@cotedIvoire,`Croatia`=@croatia,`Cuba`=@cuba,`Curacao`=@curacao,
`Cyprus`=@cyprus,`Czech Republic`=@czechRepublic,`Democratic Republic of Congo`=@democraticRepubCongo,
`Denmark`=@denmark,`Djibouti`=@djibouti,`Dominica`=@dominica,`Dominican Republic`=@dominicanRepublic,
`Ecuador`=@ecuador,`Egypt`=@egypt,`El Salvador`=@elSalvador,`Equatorial Guinea`=@equatorialGuinea,
`Eritrea`=@eritrea,`Estonia`=@estonia,`Ethiopia`=@ethiopia,`Faeroe Islands`=@faeroeIslands,
`Falkland Islands`=@falklandIslands,`Fiji`=@fiji,`Finland`=@finland,`France`=@france,
`French Polynesia`=@frenchPolynesia,
`World`= NULLIF(@world,''),`Afghanistan`= NULLIF(@afghanistan,''),`Albania`= NULLIF(@albania,''),`Algeria`= NULLIF(@algeria,''),`Andorra`= NULLIF(@andorra,''),
`Angola`= NULLIF(@angola,''),`Anguilla`= NULLIF(@anguilla,''),`Antigua and Barbuda`= NULLIF(@antiguabarbuda,''),`Argentina`= NULLIF(@argentina,''),
`Armenia`= NULLIF(@armenia,''),`Aruba`= NULLIF(@aruba,''),`Australia`= NULLIF(@australia,''),`Austria`= NULLIF(@austria,''),`Azerbaijan`= NULLIF(@azerbaijan,''),
`Bahamas`= NULLIF(@bahamas,''),`Bahrain`= NULLIF(@bahrain,''),`Bangladesh`= NULLIF(@bangladesh,''),`Barbados`= NULLIF(@barbados,''),`Belarus`= NULLIF(@belarus,''),
`Belgium`= NULLIF(@belgium,''),`Belize`= NULLIF(@belize,''),`Benin`= NULLIF(@benin,''),`Bermuda`= NULLIF(@bermuda,''),`Bhutan`= NULLIF(@bhutan,''),`Bolivia`= NULLIF(@bolivia,''),
`Bonaire Sint Eustatius and Saba`= NULLIF(@bonaire,''),`Bosnia and Herzegovina`= NULLIF(@bosnia,''),`Botswana`= NULLIF(@botswana,''),
`Brazil`= NULLIF(@brazil,''),`British Virgin Islands`= NULLIF(@british,''),`Brunei`= NULLIF(@brunei,''),`Bulgaria`= NULLIF(@bulgaria,''),
`Burkina Faso`= NULLIF(@burkinaFaso,''),`Burundi`= NULLIF(@burundi,''),`Cambodia`= NULLIF(@cambodia,''),`Cameroon`= NULLIF(@cameroon,''),`Canada`= NULLIF(@canada,''),
`Cape Verde`= NULLIF(@capeVerde,''),`Cayman Islands`= NULLIF(@caymanIslands,''),`Central African Republic`= NULLIF(@centralAfricanRepublic,''),
`Chad`= NULLIF(@chad,''),`Chile`= NULLIF(@chile,''),`China`= NULLIF(@china,''),`Colombia`= NULLIF(@colombia,''),`Comoros`= NULLIF(@comoros,''),`Congo`= NULLIF(@congo,''),
`Costa Rica`= NULLIF(@costaRica,''),`Cote d'Ivoire`= NULLIF(@cotedIvoire,''),`Croatia`= NULLIF(@croatia,''),`Cuba`= NULLIF(@cuba,''),`Curacao`= NULLIF(@curacao,''),
`Cyprus`= NULLIF(@cyprus,''),`Czech Republic`= NULLIF(@czechRepublic,''),`Democratic Republic of Congo`= NULLIF(@democraticRepubCongo,''),
`Denmark`= NULLIF(@denmark,''),`Djibouti`= NULLIF(@djibouti,''),`Dominica`= NULLIF(@dominica,''),`Dominican Republic`= NULLIF(@dominicanRepublic,''),
`Ecuador`= NULLIF(@ecuador,''),`Egypt`= NULLIF(@egypt,''),`El Salvador`= NULLIF(@elSalvador,''),`Equatorial Guinea`= NULLIF(@equatorialGuinea,''),
`Eritrea`= NULLIF(@eritrea,''),`Estonia`= NULLIF(@estonia,''),`Ethiopia`= NULLIF(@ethiopia,''),`Faeroe Islands`= NULLIF(@faeroeIslands,''),
`Falkland Islands`= NULLIF(@falklandIslands,''),`Fiji`= NULLIF(@fiji,''),`Finland`= NULLIF(@finland,''),`France`= NULLIF(@france,''),
`French Polynesia`= NULLIF(@frenchPolynesia,'');



LOAD DATA INFILE '/Users/lilybessette/eclipse-workspace/Covidify-USA/DATA/total_cases.csv' 
INTO TABLE `CovidifyUSA`.`InternationalCovidByDateStagingP2GZ` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@date,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,@dummy,
@gabon,@gambia,@georgia,@germany,@ghana,@gibraltar,@greece,@greenland,@grenada,@guam,@guatemala,@guernsey,
@guinea,@guineaBissau,@guyana,@haiti,@honduras,@hungary,@iceland,@india,@indonesia,@international,@iran,
@iraq,@ireland,@isleofMan,@israel,@italy,@jamaica,@japan,@jersey,@jordan,@kazakhstan,@kenya,@kosovo,
@kuwait,@kyrgyzstan,@laos,@latvia,@lebanon,@lesotho,@liberia,@libya,@liechtenstein,@lithuania,@luxembourg,
@macedonia,@madagascar,@malawi,@malaysia,@maldives,@mali,@malta,@mauritania,@mauritius,@mexico,@moldova,
@monaco,@mongolia,@montenegro,@montserrat,@morocco,@mozambique,@myanmar,@namibia,@nepal,@netherlands,
@newCaledonia,@newZealand,@nicaragua,@niger,@nigeria,@northernMarianaIslands,@norway,@oman,@pakistan,
@palestine,@panama,@papuaNewGuinea,@paraguay,@peru,@philippines,@poland,@portugal,@puertoRico,@qatar,
@romania,@russia,@rwanda,@saintKittsNevis,@saintLucia,@saintVincentGrenadines,@sanMarino,@saoTomePrincipe,
@saudiArabia,@senegal,@serbia,@seychelles,@sierraLeone,@singapore,@sintMaarten,@slovakia,@slovenia,
@somalia,@southAfrica,@southKorea,@southSudan,@spain,@sriLanka,@sudan,@suriname,@swaziland,@sweden,
@switzerland,@syria,@taiwan,@tajikistan,@tanzania,@thailand,@timor,@togo,@trinidadTobago,@tunisia,@turkey,
@turksCaicosIslands,@uganda,@ukraine,@unitedArabEmirates,@unitedKingdom,@unitedStates,
@unitedStatesVirginIslands,@uruguay,@uzbekistan,@vatican,@venezuela,@vietnam,@westernSahara,
@yemen,@zambia,@zimbabwe)
SET `Date`=@date,
`Gabon`=@gabon,`Gambia`=@gambia,`Georgia`=@georgia,`Germany`=@germany,`Ghana`=@ghana,`Gibraltar`=@gibraltar,
`Greece`=@greece,`Greenland`=@greenland,`Grenada`=@grenada,`Guam`=@guam,`Guatemala`=@guatemala,
`Guernsey`=@guernsey,`Guinea`=@guinea,`Guinea-Bissau`=@guineaBissau,`Guyana`=@guyana,`Haiti`=@haiti,
`Honduras`=@honduras,`Hungary`=@hungary,`Iceland`=@iceland,`India`=@india,`Indonesia`=@indonesia,
`International`=@international,`Iran`=@iran,`Iraq`=@iraq,`Ireland`=@ireland,`Isle of Man`=@isleofMan,
`Israel`=@israel,`Italy`=@italy,`Jamaica`=@jamaica,`Japan`=@japan,`Jersey`=@jersey,`Jordan`=@jordan,
`Kazakhstan`=@kazakhstan,`Kenya`=@kenya,`Kosovo`=@kosovo,`Kuwait`=@kuwait,`Kyrgyzstan`=@kyrgyzstan,
`Laos`=@laos,`Latvia`=@latvia,`Lebanon`=@lebanon,`Lesotho`=@lesotho,`Liberia`=@liberia,`Libya`=@libya,
`Liechtenstein`=@liechtenstein,`Lithuania`=@lithuania,`Luxembourg`=@luxembourg,`Macedonia`=@macedonia,
`Madagascar`=@madagascar,`Malawi`=@malawi,`Malaysia`=@malaysia,`Maldives`=@maldives,`Mali`=@mali,
`Malta`=@malta,`Mauritania`=@mauritania,`Mauritius`=@mauritius,`Mexico`=@mexico,`Moldova`=@moldova,
`Monaco`=@monaco,`Mongolia`=@mongolia,`Montenegro`=@montenegro,`Montserrat`=@montserrat,
`Morocco`=@morocco,`Mozambique`=@mozambique,`Myanmar`=@myanmar,`Namibia`=@namibia,`Nepal`=@nepal,
`Netherlands`=@netherlands,`New Caledonia`=@newCaledonia,`New Zealand`=@newZealand,`Nicaragua`=@nicaragua,
`Niger`=@niger,`Nigeria`=@nigeria,`Northern Mariana Islands`=@northernMarianaIslands,`Norway`=@norway,
`Oman`=@oman,`Pakistan`=@pakistan,`Palestine`=@palestine,`Panama`=@panama,`Papua New Guinea`=@papuaNewGuinea,
`Paraguay`=@paraguay,`Peru`=@peru,`Philippines`=@philippines,`Poland`=@poland,`Portugal`=@portugal,
`Puerto Rico`=@puertoRico,`Qatar`=@qatar,`Romania`=@romania,`Russia`=@russia,`Rwanda`=@rwanda,
`Saint Kitts and Nevis`=@saintKittsNevis,`Saint Lucia`=@saintLucia,
`Saint Vincent and the Grenadines`=@saintVincentGrenadines,`San Marino`=@sanMarino,
`Sao Tome and Principe`=@saoTomePrincipe,`Saudi Arabia`=@saudiArabia,`Senegal`=@senegal,`Serbia`=@serbia,
`Seychelles`=@seychelles,`Sierra Leone`=@sierraLeone,`Singapore`=@singapore,
`Sint Maarten (Dutch part)`=@sintMaarten,`Slovakia`=@slovakia,`Slovenia`=@slovenia,`Somalia`=@somalia,
`South Africa`=@southAfrica,`South Korea`=@southKorea,`South Sudan`=@southSudan,`Spain`=@spain,
`Sri Lanka`=@sriLanka,`Sudan`=@sudan,`Suriname`=@suriname,`Swaziland`=@swaziland,`Sweden`=@sweden,
`Switzerland`=@switzerland,`Syria`=@syria,`Taiwan`=@taiwan,`Tajikistan`=@tajikistan,`Tanzania`=@tanzania,
`Thailand`=@thailand,`Timor`=@timor,`Togo`=@togo,`Trinidad and Tobago`=@trinidadTobago,`Tunisia`=@tunisia,
`Turkey`=@turkey,`Turks and Caicos Islands`=@turksCaicosIslands,`Uganda`=@uganda,`Ukraine`=@ukraine,
`United Arab Emirates`=@unitedArabEmirates,`United Kingdom`=@unitedKingdom,`United States`=@unitedStates,
`United States Virgin Islands`=@unitedStatesVirginIslands,`Uruguay`=@uruguay,`Uzbekistan`=@uzbekistan,
`Vatican`=@vatican,`Venezuela`=@venezuela,`Vietnam`=@vietnam,`Western Sahara`=@westernSahara,
`Yemen`=@yemen,`Zambia`=@zambia,`Zimbabwe`=@zimbabwe, `Gabon`= NULLIF(@gabon,''),`Gambia`= NULLIF(@gambia,''),`Georgia`= NULLIF(@georgia,''),`Germany`= NULLIF(@germany,''),`Ghana`= NULLIF(@ghana,''),`Gibraltar`= NULLIF(@gibraltar,''),
`Greece`= NULLIF(@greece,''),`Greenland`= NULLIF(@greenland,''),`Grenada`= NULLIF(@grenada,''),`Guam`= NULLIF(@guam,''),`Guatemala`= NULLIF(@guatemala,''),
`Guernsey`= NULLIF(@guernsey,''),`Guinea`= NULLIF(@guinea,''),`Guinea-Bissau`= NULLIF(@guineaBissau,''),`Guyana`= NULLIF(@guyana,''),`Haiti`= NULLIF(@haiti,''),
`Honduras`= NULLIF(@honduras,''),`Hungary`= NULLIF(@hungary,''),`Iceland`= NULLIF(@iceland,''),`India`= NULLIF(@india,''),`Indonesia`= NULLIF(@indonesia,''),
`International`= NULLIF(@international,''),`Iran`= NULLIF(@iran,''),`Iraq`= NULLIF(@iraq,''),`Ireland`= NULLIF(@ireland,''),`Isle of Man`= NULLIF(@isleofMan,''),
`Israel`= NULLIF(@israel,''),`Italy`= NULLIF(@italy,''),`Jamaica`= NULLIF(@jamaica,''),`Japan`= NULLIF(@japan,''),`Jersey`= NULLIF(@jersey,''),`Jordan`= NULLIF(@jordan,''),
`Kazakhstan`= NULLIF(@kazakhstan,''),`Kenya`= NULLIF(@kenya,''),`Kosovo`= NULLIF(@kosovo,''),`Kuwait`= NULLIF(@kuwait,''),`Kyrgyzstan`= NULLIF(@kyrgyzstan,''),
`Laos`= NULLIF(@laos,''),`Latvia`= NULLIF(@latvia,''),`Lebanon`= NULLIF(@lebanon,''),`Lesotho`= NULLIF(@lesotho,''),`Liberia`= NULLIF(@liberia,''),`Libya`= NULLIF(@libya,''),
`Liechtenstein`= NULLIF(@liechtenstein,''),`Lithuania`= NULLIF(@lithuania,''),`Luxembourg`= NULLIF(@luxembourg,''),`Macedonia`= NULLIF(@macedonia,''),
`Madagascar`= NULLIF(@madagascar,''),`Malawi`= NULLIF(@malawi,''),`Malaysia`= NULLIF(@malaysia,''),`Maldives`= NULLIF(@maldives,''),`Mali`= NULLIF(@mali,''),
`Malta`= NULLIF(@malta,''),`Mauritania`= NULLIF(@mauritania,''),`Mauritius`= NULLIF(@mauritius,''),`Mexico`= NULLIF(@mexico,''),`Moldova`= NULLIF(@moldova,''),
`Monaco`= NULLIF(@monaco,''),`Mongolia`= NULLIF(@mongolia,''),`Montenegro`= NULLIF(@montenegro,''),`Montserrat`= NULLIF(@montserrat,''),
`Morocco`= NULLIF(@morocco,''),`Mozambique`= NULLIF(@mozambique,''),`Myanmar`= NULLIF(@myanmar,''),`Namibia`= NULLIF(@namibia,''),`Nepal`= NULLIF(@nepal,''),
`Netherlands`= NULLIF(@netherlands,''),`New Caledonia`= NULLIF(@newCaledonia,''),`New Zealand`= NULLIF(@newZealand,''),`Nicaragua`= NULLIF(@nicaragua,''),
`Niger`= NULLIF(@niger,''),`Nigeria`= NULLIF(@nigeria,''),`Northern Mariana Islands`= NULLIF(@northernMarianaIslands,''),`Norway`= NULLIF(@norway,''),
`Oman`= NULLIF(@oman,''),`Pakistan`= NULLIF(@pakistan,''),`Palestine`= NULLIF(@palestine,''),`Panama`= NULLIF(@panama,''),`Papua New Guinea`= NULLIF(@papuaNewGuinea,''),
`Paraguay`= NULLIF(@paraguay,''),`Peru`= NULLIF(@peru,''),`Philippines`= NULLIF(@philippines,''),`Poland`= NULLIF(@poland,''),`Portugal`= NULLIF(@portugal,''),
`Puerto Rico`= NULLIF(@puertoRico,''),`Qatar`= NULLIF(@qatar,''),`Romania`= NULLIF(@romania,''),`Russia`= NULLIF(@russia,''),`Rwanda`= NULLIF(@rwanda,''),
`Saint Kitts and Nevis`= NULLIF(@saintKittsNevis,''),`Saint Lucia`= NULLIF(@saintLucia,''),
`Saint Vincent and the Grenadines`= NULLIF(@saintVincentGrenadines,''),`San Marino`= NULLIF(@sanMarino,''),
`Sao Tome and Principe`= NULLIF(@saoTomePrincipe,''),`Saudi Arabia`= NULLIF(@saudiArabia,''),`Senegal`= NULLIF(@senegal,''),`Serbia`= NULLIF(@serbia,''),
`Seychelles`= NULLIF(@seychelles,''),`Sierra Leone`= NULLIF(@sierraLeone,''),`Singapore`= NULLIF(@singapore,''),
`Sint Maarten (Dutch part)`= NULLIF(@sintMaarten,''),`Slovakia`= NULLIF(@slovakia,''),`Slovenia`= NULLIF(@slovenia,''),`Somalia`= NULLIF(@somalia,''),
`South Africa`= NULLIF(@southAfrica,''),`South Korea`= NULLIF(@southKorea,''),`South Sudan`= NULLIF(@southSudan,''),`Spain`= NULLIF(@spain,''),
`Sri Lanka`= NULLIF(@sriLanka,''),`Sudan`= NULLIF(@sudan,''),`Suriname`= NULLIF(@suriname,''),`Swaziland`= NULLIF(@swaziland,''),`Sweden`= NULLIF(@sweden,''),
`Switzerland`= NULLIF(@switzerland,''),`Syria`= NULLIF(@syria,''),`Taiwan`= NULLIF(@taiwan,''),`Tajikistan`= NULLIF(@tajikistan,''),`Tanzania`= NULLIF(@tanzania,''),
`Thailand`= NULLIF(@thailand,''),`Timor`= NULLIF(@timor,''),`Togo`= NULLIF(@togo,''),`Trinidad and Tobago`= NULLIF(@trinidadTobago,''),`Tunisia`= NULLIF(@tunisia,''),
`Turkey`= NULLIF(@turkey,''),`Turks and Caicos Islands`= NULLIF(@turksCaicosIslands,''),`Uganda`= NULLIF(@uganda,''),`Ukraine`= NULLIF(@ukraine,''),
`United Arab Emirates`= NULLIF(@unitedArabEmirates,''),`United Kingdom`= NULLIF(@unitedKingdom,''),`United States`= NULLIF(@unitedStates,''),
`United States Virgin Islands`= NULLIF(@unitedStatesVirginIslands,''),`Uruguay`= NULLIF(@uruguay,''),`Uzbekistan`= NULLIF(@uzbekistan,''),
`Vatican`= NULLIF(@vatican,''),`Venezuela`= NULLIF(@venezuela,''),`Vietnam`= NULLIF(@vietnam,''),`Western Sahara`= NULLIF(@westernSahara,''),
`Yemen`= NULLIF(@yemen,''),`Zambia`= NULLIF(@zambia,''),`Zimbabwe`= NULLIF(@zimbabwe,'');

Select * from InternationalCovidByDateStagingP1AF;
Select * from InternationalCovidByDateStagingP2GZ;

Select 	`Date`, `CountryName`, `Cases` FROM (
select `Date`,"World"CountryName, `World`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Afghanistan"CountryName, `Afghanistan`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Albania"CountryName, `Albania`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Algeria"CountryName, `Algeria`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Andorra"CountryName, `Andorra`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Angola"CountryName, `Angola`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Anguilla"CountryName, `Anguilla`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Antigua and Barbuda"CountryName, `Antigua and Barbuda`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Argentina"CountryName, `Argentina`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Armenia"CountryName, `Armenia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Aruba"CountryName, `Aruba`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Australia"CountryName, `Australia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Austria"CountryName, `Austria`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Azerbaijan"CountryName, `Azerbaijan`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bahamas"CountryName, `Bahamas`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bahrain"CountryName, `Bahrain`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bangladesh"CountryName, `Bangladesh`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Barbados"CountryName, `Barbados`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Belarus"CountryName, `Belarus`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Belgium"CountryName, `Belgium`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Belize"CountryName, `Belize`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Benin"CountryName, `Benin`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bermuda"CountryName, `Bermuda`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bhutan"CountryName, `Bhutan`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bolivia"CountryName, `Bolivia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bonaire Sint Eustatius and Saba"CountryName, `Bonaire Sint Eustatius and Saba`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bosnia and Herzegovina"CountryName, `Bosnia and Herzegovina`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Botswana"CountryName, `Botswana`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Brazil"CountryName, `Brazil`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"British Virgin Islands"CountryName, `British Virgin Islands`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Brunei"CountryName, `Brunei`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bulgaria"CountryName, `Bulgaria`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Burkina Faso"CountryName, `Burkina Faso`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Burundi"CountryName, `Burundi`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cambodia"CountryName, `Cambodia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cameroon"CountryName, `Cameroon`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Canada"CountryName, `Canada`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cape Verde"CountryName, `Cape Verde`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cayman Islands"CountryName, `Cayman Islands`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Central African Republic"CountryName, `Central African Republic`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Chad"CountryName, `Chad`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Chile"CountryName, `Chile`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"China"CountryName, `China`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Colombia"CountryName, `Colombia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Comoros"CountryName, `Comoros`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Congo"CountryName, `Congo`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Costa Rica"CountryName, `Costa Rica`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cote d'Ivoire"CountryName, `Cote d'Ivoire`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Croatia"CountryName, `Croatia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cuba"CountryName, `Cuba`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Curacao"CountryName, `Curacao`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cyprus"CountryName, `Cyprus`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Czech Republic"CountryName, `Czech Republic`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Democratic Republic of Congo"CountryName, `Democratic Republic of Congo`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Denmark"CountryName, `Denmark`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Djibouti"CountryName, `Djibouti`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Dominica"CountryName, `Dominica`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Dominican Republic"CountryName, `Dominican Republic`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Ecuador"CountryName, `Ecuador`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Egypt"CountryName, `Egypt`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"El Salvador"CountryName, `El Salvador`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Equatorial Guinea"CountryName, `Equatorial Guinea`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Eritrea"CountryName, `Eritrea`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Estonia"CountryName, `Estonia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Ethiopia"CountryName, `Ethiopia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Faeroe Islands"CountryName, `Faeroe Islands`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Falkland Islands"CountryName, `Falkland Islands`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Fiji"CountryName, `Fiji`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Finland"CountryName, `Finland`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"France"CountryName, `France`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"French Polynesia"CountryName, `French Polynesia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Gabon"CountryName, `Gabon`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Gambia"CountryName, `Gambia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Georgia"CountryName, `Georgia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Germany"CountryName, `Germany`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Ghana"CountryName, `Ghana`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Gibraltar"CountryName, `Gibraltar`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Greece"CountryName, `Greece`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Greenland"CountryName, `Greenland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Grenada"CountryName, `Grenada`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guam"CountryName, `Guam`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guatemala"CountryName, `Guatemala`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guernsey"CountryName, `Guernsey`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guinea"CountryName, `Guinea`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guinea-Bissau"CountryName, `Guinea-Bissau`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guyana"CountryName, `Guyana`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Haiti"CountryName, `Haiti`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Honduras"CountryName, `Honduras`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Hungary"CountryName, `Hungary`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Iceland"CountryName, `Iceland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"India"CountryName, `India`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Indonesia"CountryName, `Indonesia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"International"CountryName, `International`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Iran"CountryName, `Iran`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Iraq"CountryName, `Iraq`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Ireland"CountryName, `Ireland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Isle of Man"CountryName, `Isle of Man`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Israel"CountryName, `Israel`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Italy"CountryName, `Italy`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Jamaica"CountryName, `Jamaica`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Japan"CountryName, `Japan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Jersey"CountryName, `Jersey`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Jordan"CountryName, `Jordan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kazakhstan"CountryName, `Kazakhstan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kenya"CountryName, `Kenya`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kosovo"CountryName, `Kosovo`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kuwait"CountryName, `Kuwait`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kyrgyzstan"CountryName, `Kyrgyzstan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Laos"CountryName, `Laos`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Latvia"CountryName, `Latvia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Lebanon"CountryName, `Lebanon`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Lesotho"CountryName, `Lesotho`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Liberia"CountryName, `Liberia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Libya"CountryName, `Libya`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Liechtenstein"CountryName, `Liechtenstein`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Lithuania"CountryName, `Lithuania`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Luxembourg"CountryName, `Luxembourg`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Macedonia"CountryName, `Macedonia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Madagascar"CountryName, `Madagascar`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Malawi"CountryName, `Malawi`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Malaysia"CountryName, `Malaysia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Maldives"CountryName, `Maldives`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mali"CountryName, `Mali`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Malta"CountryName, `Malta`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mauritania"CountryName, `Mauritania`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mauritius"CountryName, `Mauritius`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mexico"CountryName, `Mexico`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Moldova"CountryName, `Moldova`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Monaco"CountryName, `Monaco`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mongolia"CountryName, `Mongolia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Montenegro"CountryName, `Montenegro`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Montserrat"CountryName, `Montserrat`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Morocco"CountryName, `Morocco`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mozambique"CountryName, `Mozambique`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Myanmar"CountryName, `Myanmar`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Namibia"CountryName, `Namibia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Nepal"CountryName, `Nepal`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Netherlands"CountryName, `Netherlands`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"New Caledonia"CountryName, `New Caledonia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"New Zealand"CountryName, `New Zealand`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Nicaragua"CountryName, `Nicaragua`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Niger"CountryName, `Niger`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Nigeria"CountryName, `Nigeria`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Northern Mariana Islands"CountryName, `Northern Mariana Islands`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Norway"CountryName, `Norway`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Oman"CountryName, `Oman`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Pakistan"CountryName, `Pakistan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Palestine"CountryName, `Palestine`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Panama"CountryName, `Panama`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Papua New Guinea"CountryName, `Papua New Guinea`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Paraguay"CountryName, `Paraguay`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Peru"CountryName, `Peru`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Philippines"CountryName, `Philippines`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Poland"CountryName, `Poland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Portugal"CountryName, `Portugal`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Puerto Rico"CountryName, `Puerto Rico`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Qatar"CountryName, `Qatar`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Romania"CountryName, `Romania`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Russia"CountryName, `Russia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Rwanda"CountryName, `Rwanda`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Saint Kitts and Nevis"CountryName, `Saint Kitts and Nevis`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Saint Lucia"CountryName, `Saint Lucia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Saint Vincent and the Grenadines"CountryName, `Saint Vincent and the Grenadines`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"San Marino"CountryName, `San Marino`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sao Tome and Principe"CountryName, `Sao Tome and Principe`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Saudi Arabia"CountryName, `Saudi Arabia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Senegal"CountryName, `Senegal`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Serbia"CountryName, `Serbia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Seychelles"CountryName, `Seychelles`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sierra Leone"CountryName, `Sierra Leone`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Singapore"CountryName, `Singapore`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sint Maarten (Dutch part)"CountryName, `Sint Maarten (Dutch part)`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Slovakia"CountryName, `Slovakia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Slovenia"CountryName, `Slovenia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Somalia"CountryName, `Somalia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"South Africa"CountryName, `South Africa`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"South Korea"CountryName, `South Korea`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"South Sudan"CountryName, `South Sudan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Spain"CountryName, `Spain`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sri Lanka"CountryName, `Sri Lanka`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sudan"CountryName, `Sudan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Suriname"CountryName, `Suriname`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Swaziland"CountryName, `Swaziland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sweden"CountryName, `Sweden`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Switzerland"CountryName, `Switzerland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Syria"CountryName, `Syria`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Taiwan"CountryName, `Taiwan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Tajikistan"CountryName, `Tajikistan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Tanzania"CountryName, `Tanzania`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Thailand"CountryName, `Thailand`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Timor"CountryName, `Timor`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Togo"CountryName, `Togo`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Trinidad and Tobago"CountryName, `Trinidad and Tobago`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Tunisia"CountryName, `Tunisia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Turkey"CountryName, `Turkey`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Turks and Caicos Islands"CountryName, `Turks and Caicos Islands`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Uganda"CountryName, `Uganda`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Ukraine"CountryName, `Ukraine`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"United Arab Emirates"CountryName, `United Arab Emirates`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"United Kingdom"CountryName, `United Kingdom`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"United States"CountryName, `United States`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"United States Virgin Islands"CountryName, `United States Virgin Islands`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Uruguay"CountryName, `Uruguay`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Uzbekistan"CountryName, `Uzbekistan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Vatican"CountryName, `Vatican`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Venezuela"CountryName, `Venezuela`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Vietnam"CountryName, `Vietnam`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Western Sahara"CountryName, `Western Sahara`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Yemen"CountryName, `Yemen`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Zambia"CountryName, `Zambia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Zimbabwe"CountryName, `Zimbabwe`Cases  from InternationalCovidByDateStagingP2GZ) PivottedTable;

#--- final 

INSERT INTO `CovidifyUSA`.`InternationalCovidByDateStaging` 
(`Date`, `CountryName`,`CovidCases`)
SELECT Date, CountryName, Cases FROM (
select `Date`,"World"CountryName, `World`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Afghanistan"CountryName, `Afghanistan`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Albania"CountryName, `Albania`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Algeria"CountryName, `Algeria`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Andorra"CountryName, `Andorra`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Angola"CountryName, `Angola`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Anguilla"CountryName, `Anguilla`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Antigua and Barbuda"CountryName, `Antigua and Barbuda`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Argentina"CountryName, `Argentina`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Armenia"CountryName, `Armenia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Aruba"CountryName, `Aruba`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Australia"CountryName, `Australia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Austria"CountryName, `Austria`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Azerbaijan"CountryName, `Azerbaijan`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bahamas"CountryName, `Bahamas`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bahrain"CountryName, `Bahrain`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bangladesh"CountryName, `Bangladesh`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Barbados"CountryName, `Barbados`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Belarus"CountryName, `Belarus`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Belgium"CountryName, `Belgium`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Belize"CountryName, `Belize`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Benin"CountryName, `Benin`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bermuda"CountryName, `Bermuda`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bhutan"CountryName, `Bhutan`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bolivia"CountryName, `Bolivia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bonaire Sint Eustatius and Saba"CountryName, `Bonaire Sint Eustatius and Saba`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bosnia and Herzegovina"CountryName, `Bosnia and Herzegovina`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Botswana"CountryName, `Botswana`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Brazil"CountryName, `Brazil`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"British Virgin Islands"CountryName, `British Virgin Islands`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Brunei"CountryName, `Brunei`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Bulgaria"CountryName, `Bulgaria`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Burkina Faso"CountryName, `Burkina Faso`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Burundi"CountryName, `Burundi`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cambodia"CountryName, `Cambodia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cameroon"CountryName, `Cameroon`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Canada"CountryName, `Canada`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cape Verde"CountryName, `Cape Verde`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cayman Islands"CountryName, `Cayman Islands`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Central African Republic"CountryName, `Central African Republic`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Chad"CountryName, `Chad`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Chile"CountryName, `Chile`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"China"CountryName, `China`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Colombia"CountryName, `Colombia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Comoros"CountryName, `Comoros`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Congo"CountryName, `Congo`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Costa Rica"CountryName, `Costa Rica`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cote d'Ivoire"CountryName, `Cote d'Ivoire`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Croatia"CountryName, `Croatia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cuba"CountryName, `Cuba`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Curacao"CountryName, `Curacao`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Cyprus"CountryName, `Cyprus`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Czech Republic"CountryName, `Czech Republic`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Democratic Republic of Congo"CountryName, `Democratic Republic of Congo`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Denmark"CountryName, `Denmark`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Djibouti"CountryName, `Djibouti`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Dominica"CountryName, `Dominica`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Dominican Republic"CountryName, `Dominican Republic`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Ecuador"CountryName, `Ecuador`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Egypt"CountryName, `Egypt`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"El Salvador"CountryName, `El Salvador`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Equatorial Guinea"CountryName, `Equatorial Guinea`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Eritrea"CountryName, `Eritrea`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Estonia"CountryName, `Estonia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Ethiopia"CountryName, `Ethiopia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Faeroe Islands"CountryName, `Faeroe Islands`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Falkland Islands"CountryName, `Falkland Islands`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Fiji"CountryName, `Fiji`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Finland"CountryName, `Finland`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"France"CountryName, `France`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"French Polynesia"CountryName, `French Polynesia`Cases  from InternationalCovidByDateStagingP1AF union all 
select `Date`,"Gabon"CountryName, `Gabon`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Gambia"CountryName, `Gambia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Georgia"CountryName, `Georgia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Germany"CountryName, `Germany`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Ghana"CountryName, `Ghana`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Gibraltar"CountryName, `Gibraltar`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Greece"CountryName, `Greece`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Greenland"CountryName, `Greenland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Grenada"CountryName, `Grenada`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guam"CountryName, `Guam`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guatemala"CountryName, `Guatemala`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guernsey"CountryName, `Guernsey`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guinea"CountryName, `Guinea`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guinea-Bissau"CountryName, `Guinea-Bissau`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Guyana"CountryName, `Guyana`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Haiti"CountryName, `Haiti`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Honduras"CountryName, `Honduras`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Hungary"CountryName, `Hungary`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Iceland"CountryName, `Iceland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"India"CountryName, `India`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Indonesia"CountryName, `Indonesia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"International"CountryName, `International`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Iran"CountryName, `Iran`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Iraq"CountryName, `Iraq`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Ireland"CountryName, `Ireland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Isle of Man"CountryName, `Isle of Man`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Israel"CountryName, `Israel`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Italy"CountryName, `Italy`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Jamaica"CountryName, `Jamaica`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Japan"CountryName, `Japan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Jersey"CountryName, `Jersey`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Jordan"CountryName, `Jordan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kazakhstan"CountryName, `Kazakhstan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kenya"CountryName, `Kenya`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kosovo"CountryName, `Kosovo`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kuwait"CountryName, `Kuwait`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Kyrgyzstan"CountryName, `Kyrgyzstan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Laos"CountryName, `Laos`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Latvia"CountryName, `Latvia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Lebanon"CountryName, `Lebanon`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Lesotho"CountryName, `Lesotho`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Liberia"CountryName, `Liberia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Libya"CountryName, `Libya`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Liechtenstein"CountryName, `Liechtenstein`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Lithuania"CountryName, `Lithuania`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Luxembourg"CountryName, `Luxembourg`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Macedonia"CountryName, `Macedonia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Madagascar"CountryName, `Madagascar`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Malawi"CountryName, `Malawi`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Malaysia"CountryName, `Malaysia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Maldives"CountryName, `Maldives`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mali"CountryName, `Mali`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Malta"CountryName, `Malta`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mauritania"CountryName, `Mauritania`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mauritius"CountryName, `Mauritius`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mexico"CountryName, `Mexico`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Moldova"CountryName, `Moldova`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Monaco"CountryName, `Monaco`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mongolia"CountryName, `Mongolia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Montenegro"CountryName, `Montenegro`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Montserrat"CountryName, `Montserrat`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Morocco"CountryName, `Morocco`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Mozambique"CountryName, `Mozambique`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Myanmar"CountryName, `Myanmar`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Namibia"CountryName, `Namibia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Nepal"CountryName, `Nepal`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Netherlands"CountryName, `Netherlands`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"New Caledonia"CountryName, `New Caledonia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"New Zealand"CountryName, `New Zealand`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Nicaragua"CountryName, `Nicaragua`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Niger"CountryName, `Niger`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Nigeria"CountryName, `Nigeria`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Northern Mariana Islands"CountryName, `Northern Mariana Islands`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Norway"CountryName, `Norway`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Oman"CountryName, `Oman`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Pakistan"CountryName, `Pakistan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Palestine"CountryName, `Palestine`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Panama"CountryName, `Panama`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Papua New Guinea"CountryName, `Papua New Guinea`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Paraguay"CountryName, `Paraguay`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Peru"CountryName, `Peru`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Philippines"CountryName, `Philippines`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Poland"CountryName, `Poland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Portugal"CountryName, `Portugal`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Puerto Rico"CountryName, `Puerto Rico`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Qatar"CountryName, `Qatar`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Romania"CountryName, `Romania`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Russia"CountryName, `Russia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Rwanda"CountryName, `Rwanda`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Saint Kitts and Nevis"CountryName, `Saint Kitts and Nevis`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Saint Lucia"CountryName, `Saint Lucia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Saint Vincent and the Grenadines"CountryName, `Saint Vincent and the Grenadines`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"San Marino"CountryName, `San Marino`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sao Tome and Principe"CountryName, `Sao Tome and Principe`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Saudi Arabia"CountryName, `Saudi Arabia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Senegal"CountryName, `Senegal`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Serbia"CountryName, `Serbia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Seychelles"CountryName, `Seychelles`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sierra Leone"CountryName, `Sierra Leone`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Singapore"CountryName, `Singapore`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sint Maarten (Dutch part)"CountryName, `Sint Maarten (Dutch part)`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Slovakia"CountryName, `Slovakia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Slovenia"CountryName, `Slovenia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Somalia"CountryName, `Somalia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"South Africa"CountryName, `South Africa`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"South Korea"CountryName, `South Korea`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"South Sudan"CountryName, `South Sudan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Spain"CountryName, `Spain`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sri Lanka"CountryName, `Sri Lanka`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sudan"CountryName, `Sudan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Suriname"CountryName, `Suriname`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Swaziland"CountryName, `Swaziland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Sweden"CountryName, `Sweden`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Switzerland"CountryName, `Switzerland`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Syria"CountryName, `Syria`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Taiwan"CountryName, `Taiwan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Tajikistan"CountryName, `Tajikistan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Tanzania"CountryName, `Tanzania`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Thailand"CountryName, `Thailand`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Timor"CountryName, `Timor`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Togo"CountryName, `Togo`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Trinidad and Tobago"CountryName, `Trinidad and Tobago`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Tunisia"CountryName, `Tunisia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Turkey"CountryName, `Turkey`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Turks and Caicos Islands"CountryName, `Turks and Caicos Islands`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Uganda"CountryName, `Uganda`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Ukraine"CountryName, `Ukraine`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"United Arab Emirates"CountryName, `United Arab Emirates`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"United Kingdom"CountryName, `United Kingdom`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"United States"CountryName, `United States`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"United States Virgin Islands"CountryName, `United States Virgin Islands`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Uruguay"CountryName, `Uruguay`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Uzbekistan"CountryName, `Uzbekistan`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Vatican"CountryName, `Vatican`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Venezuela"CountryName, `Venezuela`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Vietnam"CountryName, `Vietnam`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Western Sahara"CountryName, `Western Sahara`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Yemen"CountryName, `Yemen`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Zambia"CountryName, `Zambia`Cases  from InternationalCovidByDateStagingP2GZ union all 
select `Date`,"Zimbabwe"CountryName, `Zimbabwe`Cases  from InternationalCovidByDateStagingP2GZ) as PivottedTable;


SELECT * From InternationalCovidByDateStaging
INTO OUTFILE './04InternationalCovidByDateStaging.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';

Select * from InternationalCovidByDateStaging;

#TODO Select statements for most recent date and order by top 10 countries
#TODO potentialltally make curves for the countries to compare plot to US curve

INSERT INTO `CovidifyUSA`.`Country` 
(`CountryName`)
SELECT CountryName FROM InternationalCovidByDateStaging GROUP BY CountryName;

Select * from Country;

INSERT INTO `CovidifyUSA`.`InternationalCovidByDate` 
(`Date`, `CountryFKey`,`CovidCases`)
Select Date, CountryKey,  CovidCases FROM
(Select * From InternationalCovidByDateStaging ) as s1 
Inner join
(select * From Country) as s2 where s1.CountryName = s2.CountryName;

Select * from InternationalCovidByDate;

SELECT * From InternationalCovidByDate
INTO OUTFILE './04InternationalCovidByDate.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';


Select s1.InternationalCovidByDateKey,s1.Date, s1.CountryFKey, s2.CountryName, s1.CovidCases FROM
(Select InternationalCovidByDateKey,  Date, CountryFKey,CovidCases From InternationalCovidByDate ) as s1 
Inner join
(select CountryKey, CountryName From Country) as s2 where s1.CountryFKey = s2.CountryKey;

Select s1.InternationalCovidByDateKey, s1.Date, s1.CountryFKey, s2.CountryName, s1.CovidCases FROM
(Select  InternationalCovidByDateKey, Date, CountryFKey,CovidCases From InternationalCovidByDate ) as s1 
Inner join
(select CountryKey, CountryName From Country) as s2 where s1.CountryFKey = s2.CountryKey
INTO OUTFILE './04InternationalCovidByDateCountryNames.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';

select MostRecent,CountryFKey,`COVID-19 Cases`, CountryName From
(select CountryKey, CountryName from Country) as d2 
inner join
(Select MAX(Date) as MostRecent, CountryFKey, MAX(CovidCases) as `COVID-19 Cases`
From InternationalCovidByDate 
GROUP BY CountryFKey 
Order by MAX(CovidCases) desc) as d1
on d1.CountryFKey = d2.CountryKey 
order by `COVID-19 Cases` desc
INTO OUTFILE './04MostRecentDateCountryOrderByCovidCases.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';

(select CountryFKey,CountryName from (
select MostRecent,CountryFKey,`COVID-19 Cases`, CountryName From
(select CountryKey, CountryName from Country) as d2 
inner join
(Select MAX(Date) as MostRecent, CountryFKey, MAX(CovidCases) as `COVID-19 Cases`
From InternationalCovidByDate 
GROUP BY CountryFKey 
Order by MAX(CovidCases) desc) as d1
on d1.CountryFKey = d2.CountryKey 
order by `COVID-19 Cases` desc
Limit 11) as topcurrentcases);

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`HighestCasesInternational` (  
`HighestCasesInternationalKey` INT NOT NULL AUTO_INCREMENT,  
`MostRecentDateInternational` Date,  `CountryFKey` Int not null,  `International COVID-19 Cases` INT NULL, `CountryName` TEXT ,
PRIMARY KEY (`HighestCasesInternationalKey`))
ENGINE = InnoDB;  

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`HighestCasesNational` (  
`HighestCasesNationalKey` INT NOT NULL AUTO_INCREMENT,  
`MostRecentDateNational` DATE, `National COVID-19 Cases` INT NULL, `StateName` TEXT , `StateFKey` int not null,
PRIMARY KEY (`HighestCasesNationalKey`))
ENGINE = InnoDB;  

INSERT INTO `CovidifyUSA`.`HighestCasesInternational` 
(`MostRecentDateInternational`, `CountryFKey` , `International COVID-19 Cases`, `CountryName`)
SELECT MostRecent,CountryFKey, `COVID-19 Cases`, CountryName FROM (
select MostRecent,CountryFKey,`COVID-19 Cases`, CountryName From
(select CountryKey, CountryName from Country) as d2 
inner join
(Select MAX(Date) as MostRecent, CountryFKey, MAX(CovidCases) as `COVID-19 Cases`
From InternationalCovidByDate 
GROUP BY CountryFKey 
Order by MAX(CovidCases) desc) as d1
on d1.CountryFKey = d2.CountryKey 
order by `COVID-19 Cases` desc
limit 51) as highestinternational;


INSERT INTO `CovidifyUSA`.`HighestCasesNational` 
(  `MostRecentDateNational`, `National COVID-19 Cases`, `StateName`, `StateFKey`)
SELECT MostRecent, `State COVID-19 Cases`, StateName, StateFKey FROM (
Select  MAX(Date) as MostRecent, MAX(CovidCases) as `State COVID-19 Cases`, StateName, StateFKey
From (select * from 
(Select * from
(select * From CovidByDate) as coviddate inner join
(select * from County) as county on coviddate.CountyFKey = county.CountyKey) as countycovid
inner join (select * from State) as state on state.StateKey = countycovid.StateFKey) as statecovid
GROUP BY StateFKey 
Order by MAX(CovidCases) desc
limit 51) as highestnational;




select * from
(select * from HighestCasesInternational ) as s1
inner join (select * from HighestCasesNational ) as s2
on s1.HighestCasesInternationalKey = s2.HighestCasesNationalKey
INTO OUTFILE './04HighestCovidCases.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';
 
select * from
(select * from country where CountryName = "United States") as usa inner join (select * from InternationalCovidByDate ) as usacovid
INTO OUTFILE './04USACovid.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';

select * from
(select * from country where CountryName = "Brazil") as usa inner join (select * from InternationalCovidByDate ) as brazilcovid
INTO OUTFILE './04BrazilCovid.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';