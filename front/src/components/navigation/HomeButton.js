import classes from "./HomeButton.module.css";

const HomeButton = (props) => {
  return (
    <button
      className={`${classes["home-btn-box"]} btn`}
      onClick={() => props.onClick()}
    >
      <img
        src={`${process.env.PUBLIC_URL}/assets/images/logo/e-pavement_logo_white.png`}
        alt="E-Pavement Company Logo"
        className={classes["logo"]}
      />
    </button>
  );
};

export default HomeButton;
