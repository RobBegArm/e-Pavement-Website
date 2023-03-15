const MainSection = (props) => {
  return (
    <section
      name={props.name}
      style={{ minHeight: "93vh" }}
      className={props.className}
    >
      {/* If the menu is shown, then do not show the content inside the section */}
      <div className={props.menuIsShown ? "none" : "content"}>
        {props.children}
      </div>
    </section>
  );
};

export default MainSection;
